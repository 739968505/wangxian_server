package com.fy.engineserver.sprite.npc;

import java.util.ArrayList;
import java.util.Random;

import com.fy.engineserver.cityfight.CityFightManager;
import com.fy.engineserver.core.Game;
import com.fy.engineserver.core.LivingObject;
import com.fy.engineserver.core.g2d.Point;
import com.fy.engineserver.datasource.career.CareerManager;
import com.fy.engineserver.datasource.skill.ActiveSkill;
import com.fy.engineserver.datasource.skill.ActiveSkillAgent;
import com.fy.engineserver.datasource.skill.AuraSkill;
import com.fy.engineserver.datasource.skill.AuraSkillAgent;
import com.fy.engineserver.datasource.skill.PassiveSkill;
import com.fy.engineserver.datasource.skill.Skill;
import com.fy.engineserver.message.GameMessageFactory;
import com.fy.engineserver.message.TRANSIENTENEMY_CHANGE_REQ;
import com.fy.engineserver.sprite.Fighter;
import com.fy.engineserver.sprite.Player;
import com.fy.engineserver.sprite.npc.GuardNPC.DamageRecord;
import com.fy.engineserver.sprite.npc.npcaction.NpcExecuteItem;
import com.fy.engineserver.sprite.pet.Pet;
import com.fy.engineserver.zongzu.data.ZongPai;
import com.fy.engineserver.zongzu.manager.ZongPaiManager;

/**
 * 城战刷新npc
 * 
 *
 */
public class CityFightNPC extends NPC implements FightableNPC{

	/**
	 * 
	 */
	private static final long serialVersionUID = 64281266653370996L;

	/**
	 * 拥有者id，谁该享有杀死这个npc的奖励
	 */
	public long ownerId;

	/**
	 * 敌对宗派id
	 */
	public long diduiZPId;
	
	public int standCountry;
	
	public String cityName;
	
	public long bornTime;
	
	///////////下面为npc战斗相关
	
	@Override
	public int getHpRecoverBase() {
		// TODO Auto-generated method stub
		return 10000;
	}
	/**
	 * 怪装备的技能ID列表，如果对应的技能为主动技能，那么就是怪攻击时
	 * 使用的技能。
	 * 
	 * 如果对应的光环类技能，那么就是怪拥有的光环类技能。
	 */
	private int activeSkillIds[];
	
	/**
	 * 怪装备的技能ID列表，对应的各个技能的级别
	 */
	protected int activeSkillLevels[];

	/**
	 * 这个怪物拥有的技能
	 */
	protected transient ArrayList<ActiveSkill> skillList = new ArrayList<ActiveSkill>();
	
	//技能代理，怪使用技能的代理
	protected transient ActiveSkillAgent skillAgent = new ActiveSkillAgent(this);
	
	//战斗代理
	protected transient NPCFightingAgent fightingAgent = new NPCFightingAgent(this);

	private transient AuraSkillAgent auraSkillAgent = new AuraSkillAgent(this);
	
	private transient AuraSkill auraSkill = null;
	/**
	 * 记录各个攻击者对我的伤害
	 */
	protected transient ArrayList<DamageRecord> hatridList = new ArrayList<DamageRecord>();
	
	//怪的内部状态，注意此状态, 0表示空闲，1表示激活，2表示回程
	protected transient int innerState = 0;
	
	/**
	 * 怪出生后，调用此初始化方法
	 */
	public void init(){
		super.init();
		CareerManager cm = CareerManager.getInstance();
		
		if(activeSkillIds == null || activeSkillIds.length == 0){
			//
			Skill[] skills = cm.getMonsterSkills();
			for(int i = skills.length -1  ; i >= 0 ; i--){
				if(skills[i] instanceof ActiveSkill){
					activeSkillIds = new int[]{skills[i].getId()};
					break;
				}
			}
		}
		
		for(int i = 0 ; i < activeSkillIds.length ; i++){
			Skill skill = cm.getSkillById(activeSkillIds[i]);
			if(skill != null && skill instanceof ActiveSkill){
				skillList.add((ActiveSkill)skill);
			}else if(skill != null && skill instanceof PassiveSkill){

			}else if(skill != null && skill instanceof AuraSkill){
				auraSkill = (AuraSkill)skill;
			}
		}
		if(auraSkill != null){
			this.auraSkillAgent.openAuraSkill(auraSkill);
		}
		
		this.commonAttackSpeed = 2000;
	}
	
	/**
	 * 某个敌人，选择某个技能
	 * @param target
	 * @return
	 */
	protected ActiveSkill getActiveSkill(Fighter target){
		if(skillList.size() > 0) return skillList.get(0);
		return null;
	}

	/**
	 * 得到最大的仇恨值敌人
	 * @return
	 */
	public Fighter getMaxHatredFighter(){
		int maxHatred = -1;
		DamageRecord d = null;
		for(int i = 0 ; i < hatridList.size() ; i++){
			DamageRecord dr = hatridList.get(i);
			if(dr.hatred > maxHatred){
				maxHatred = dr.hatred;
				d = dr;
			}
		}
		if(d != null){
			return d.f;
		}else{
			return null;
		}
	}
	/**
	 * 更新仇恨列表
	 * @param caster
	 * @param damage
	 * @param hatred
	 */
	protected void updateDamageRecord(Fighter caster,int damage,int hatred){
		
		try{
			if(caster.isDeath()) return;
			
			if(caster instanceof Player){
				hatred = hatred * (((Player)caster).getHatridRate() + 100)/100;
			}
			
			DamageRecord record = null;
			for(int i = 0 ; i < hatridList.size() ; i++){
				DamageRecord dr = hatridList.get(i);
				if(dr.f == caster){
					record = dr;
				}
			}
			if(record == null){
				record = new DamageRecord(caster,damage,hatred);
				hatridList.add(record);
			}else{
				record.damage += damage;
				record.hatred += hatred;
				record.time = heartBeatStartTime;
			}
		}catch(Exception ex){
			
		}

	}
	
	/**
	 * 处理其它生物对此生物造成伤害的事件
	 * 
	 * @param caster
	 *            伤害施加者
	 * @param damage
	 *            预期伤害值
	 * @param damageType
	 *            伤害类型，如：普通物理伤害，魔法伤害，反噬伤害等
	 */
	public void causeDamage(Fighter caster, int damage, int hateParam,int damageType) {
		//debug dot
		super.causeDamage(caster, damage, hateParam, damageType);
		if (damageType != Fighter.DAMAGETYPE_DODGE && damageType != Fighter.DAMAGETYPE_MIANYI && damageType != Fighter.DAMAGETYPE_MISS && damageType != Fighter.DAMAGETYPE_XISHOU) {
			updateDamageRecord(caster, damage, damage * hateParam);
			if (caster instanceof Pet) {
				if (((Pet) caster).getMaster() != null) {
					updateDamageRecord(((Pet) caster).getMaster(), 1, 1);
				}
			}
		}
	}

	/**
	 * 处理伤害反馈事件。当某个精灵（玩家、怪物等）受到攻击并造成伤害，<br>
	 * 该精灵会调用攻击者的这个方法，通知攻击者，它的攻击对其他精灵造成了伤害
	 * 
	 * @param target
	 *            受到伤害的目标精灵
	 * @param damage
	 *            真实伤害值
	 */
	public void damageFeedback(Fighter target, int damage,int hateParam,int damageType) {
		this.fightingAgent.damageFeedback(target, damage, damageType);
	}

	/**
	 * 在心跳函数中调用此方法
	 * 
	 * 表明此怪被杀死，此方法只能被调用一次
	 * 
	 * 此方法将处理经验值掉落，物品掉落等。
	 */
	protected void killed(long heartBeatStartTime, long interval, Game game) {
		try {
			CityFightManager cfm = CityFightManager.getInstance();
			cfm.通知攻击方胜利(standCountry, cityName, diduiZPId);
			
			
			cfm.notifyGameKickPlayer(game);
			CityFightManager.logger.warn("[战斗胜利] [成功] ["+standCountry+"] ["+cityName+"] ["+diduiZPId+"]");
		} catch (Exception e) {
			CityFightManager.logger.error("[战斗胜利] [异常] ["+standCountry+"] ["+cityName+"] ["+diduiZPId+"]",e);
		}
	}

	Random random = new Random(com.fy.engineserver.gametime.SystemTime.currentTimeMillis());
	public Point 得到位置() {
		Point point = new Point();
		point.x = this.x + random.nextInt(100);
		point.y = this.y + random.nextInt(100);
		return point;
	}

	/**
	 * 攻击目标消失，将此目标从仇恨列表中清除
	 * 
	 * @param target
	 */
	public void targetDisappear(Fighter target){
		DamageRecord record = null;
		for(int i = 0 ; i < hatridList.size() ; i++){
			DamageRecord dr = hatridList.get(i);
			if(dr.f == target){
				record = dr;
			}
		}
		if(record != null){
			hatridList.remove(record);
		}
		
		//通知更新敌人列表
		target.notifyEndToBeFighted(this);
		this.notifyEndToFighting(target);
	}

	@Override
	/**
	 * 封印任务刷新出的奸细npc
	 * 只能是发布任务的本国未到封印级别的人才可以攻击这个npc
	 */
	public int getFightingType(Fighter fighter) {
		if(!(fighter instanceof Player) && !(fighter instanceof Pet)){
			return Fighter.FIGHTING_TYPE_NEUTRAL;
		}
		if(fighter instanceof Player){
			Player p = (Player)fighter;
			ZongPaiManager zpm = ZongPaiManager.getInstance();
			if(zpm == null){
				return Fighter.FIGHTING_TYPE_NEUTRAL;
			}
			ZongPai zp = zpm.getZongPaiByPlayerId(p.getId());
			if(zp == null){
				return Fighter.FIGHTING_TYPE_NEUTRAL;
			}
			if(zp.getId() == diduiZPId){
				return Fighter.FIGHTING_TYPE_ENEMY;
			}
		}
		if(fighter instanceof Pet){
			Player p = ((Pet)fighter).getMaster();
			if(p != null){
				ZongPaiManager zpm = ZongPaiManager.getInstance();
				if(zpm == null){
					return Fighter.FIGHTING_TYPE_NEUTRAL;
				}
				ZongPai zp = zpm.getZongPaiByPlayerId(p.getId());
				if(zp == null){
					return Fighter.FIGHTING_TYPE_NEUTRAL;
				}
				if(zp.getId() == diduiZPId){
					return Fighter.FIGHTING_TYPE_ENEMY;
				}
			}else{
				return Fighter.FIGHTING_TYPE_NEUTRAL;
			}
		}
		return Fighter.FIGHTING_TYPE_NEUTRAL;
	}
	long test = 0;
	boolean notify = false;
	long lastTimeForRecoverHP;
	@Override
	public void heartbeat(long heartBeatStartTime, long interval, Game game) {
		if(heartBeatStartTime - bornTime >= CityFightManager.npc存活时间){
			if(!notify){
				notify = true;
				try {
					CityFightManager cfm = CityFightManager.getInstance();
					cfm.通知攻击方失败(standCountry, cityName, diduiZPId);
					cfm.notifyGameKickPlayer(game);
					CityFightManager.logger.warn("[时间到通知战斗失败] [成功] ["+standCountry+"] ["+cityName+"] ["+diduiZPId+"]");
				} catch (Exception e) {
					CityFightManager.logger.error("[时间到通知战斗失败] [异常] ["+standCountry+"] ["+cityName+"] ["+diduiZPId+"]",e);
				}
			}
			game.removeSprite(this);
			return;
		}
		super.heartbeat(heartBeatStartTime, interval, game);
		this.notifyPlayerAndAddTransientEnemyList(game);
		if(heartBeatStartTime > test + 6000){
			test = heartBeatStartTime;
			if(Game.logger.isInfoEnabled())
				Game.logger.info(this.name+"在"+game.gi.getName()+"的("+x+"m"+y+")");
		}
		//定身或者眩晕的情况下，停止移动
		if(this.isHold() || this.isStun()){
			if(this.getMoveTrace() != null){
				stopAndNotifyOthers();
			}
		}
		
		if(heartBeatStartTime - lastTimeForRecoverHP > 1000){
			lastTimeForRecoverHP = heartBeatStartTime;
			if(this.getHp() > 0){
				this.setHp(this.getHp() + this.getHpRecoverBase());
				if(this.getHp() > this.getMaxHP()){
					this.setHp(this.getMaxHP());
				}
			}
		}

		this.skillAgent.heartbeat(game);
		this.fightingAgent.heartbeat(heartBeatStartTime, game);

		//光环技能
		auraSkillAgent.heartbeat(heartBeatStartTime, interval, game);
		if(innerState == 0){
			Fighter f = findTargetInActivationRange(game);
			if(f != null){
				updateDamageRecord(f,0,1);
			}
			if(hatridList.size() == 0){

			}else{
				stopAndNotifyOthers();
				innerState = 1;
			}
		}

		if(innerState == 1){
			if(hatridList.size() == 0){
//				if(this.isHold() == false && this.isStun() == false){
					innerState = 0;
//				}
			}
		}

		if (state != STATE_DEAD) {
			if(getHp() > 0){
				Fighter target = getMaxHatredFighter();
				if(target instanceof Player){
					ownerId = target.getId();
				}
				if(target instanceof Pet){
					ownerId = ((Pet)target).getOwnerId();
				}
			}
			
		}
		
		
		// 检查仇恨列表
		try{
			DamageRecord drs[] = hatridList.toArray(new DamageRecord[0]);
			for (int i = 0; i < drs.length; i++) {
				if (drs[i].f == null || drs[i].f.isDeath() || game.contains((LivingObject) drs[i].f) == false) {
					hatridList.remove(drs[i]);
				}
			}
		}catch(Exception ex){
			
		}
		
	}
	
	/**
	 * 通知玩家并且加入玩家临时敌人列表
	 * @param fighter
	 */
	public void notifyPlayerAndAddTransientEnemyList(Game game){
		LivingObject[] los = game.getLivingObjects();
		if(los == null){
			return;
		}
		for(int i = 0; i < los.length; i++){
			if(los[i] instanceof Player){
				Player fighter = (Player)los[i];
				if(getFightingType(fighter) == Fighter.FIGHTING_TYPE_ENEMY){
					if(los[i].getX() >= this.x - 600 && los[i].getX() <= this.x + 600 
							&& los[i].getY() >= this.y - 600 && los[i].getY() <= this.y + 600 ){
						synchronized(fighter.transientEnemyList){
							if(fighter.transientEnemyList == null || !fighter.transientEnemyList.contains(this)){
								TRANSIENTENEMY_CHANGE_REQ req = new TRANSIENTENEMY_CHANGE_REQ(GameMessageFactory.nextSequnceNum(),
										(byte) 0, (byte) 1, this.getId());
								fighter.addMessageToRightBag(req);
								if(fighter.transientEnemyList == null){
									fighter.transientEnemyList = new ArrayList<Fighter>();
								}
								fighter.transientEnemyList.add(this);
							}
						}
					}
				}
			}
		}

	}
	int activationWidth = 600;
	int activationHeight = 600;
	/**
	 * 在激活范围内，寻找可攻击的对象，
	 * 如果范围内没有可攻击的对象，
	 * 就查看同类的怪，是否有被其他玩家攻击，如果有，协调攻击。
	 * @return
	 */
	protected Fighter findTargetInActivationRange(Game game){
//			Fighter fs[] = game.getVisbleFighter(this, false);
			LivingObject[] los = game.getLivingObjects();
			if(los == null){
				return null;
			}
			Fighter f = null;
			int minD = 0;
			for(int i = 0 ; i < los.length ; i++){
				if(los[i] instanceof Fighter){
					int ft = this.getFightingType((Fighter)los[i]);
					if(ft == Fighter.FIGHTING_TYPE_ENEMY){
						if(((Fighter)los[i]).getHp() > 0 && los[i].getX() >= this.x - this.activationWidth/2 && los[i].getX() <= this.x + this.activationWidth/2 
								&& los[i].getY() >= this.y - this.activationHeight/2 && los[i].getY() <= this.y + this.activationHeight/2 ){
								int d = (los[i].getX() - this.x)*(los[i].getX() - this.x) + (los[i].getY() - this.y)*(los[i].getY() - this.y);
								if(f == null){
									f = (Fighter)los[i];
									minD = d;
								}else if(d < minD){
									f = (Fighter)los[i];
									minD = d;
								}
						}
					}
				}
			}
			if(f != null){
				return f;
			}
		return null;
	}

	public int getSkillLevelById(int sid){
		for(int i = 0 ; activeSkillIds != null && i < this.activeSkillIds.length ; i++){
			if(activeSkillIds[i] == sid && this.activeSkillLevels != null && i < this.activeSkillLevels.length){
				if( this.activeSkillLevels[i] == 0) return 1;
				return activeSkillLevels[i] ;
			}
		}
		return 0;
	}

	/**
	 * clone出一个对象
	 */
	public Object clone() {
		CityFightNPC p = new CityFightNPC();

		p.cloneAllInitNumericalProperty(this);

		p.country = country;
		p.npcMark = npcMark;
		p.hpMark = hpMark;
		p.apMark = apMark;
		p.setnPCCategoryId(this.getnPCCategoryId());

		if (items != null) {
			p.items = new NpcExecuteItem[this.items.length];
			for (int i = 0; i < items.length; i++) {
				if (items[i] != null) {
					try {
						p.items[i] = (NpcExecuteItem) items[i].clone();
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		p.windowId = windowId;
		p.id = nextId();
		return p;
	}

	public ActiveSkillAgent getActiveSkillAgent() {
		return this.skillAgent;
	}

	@Override
	public int getPursueHeight() {
		// TODO Auto-generated method stub
		return activationHeight;
	}

	@Override
	public int getPursueWidth() {
		// TODO Auto-generated method stub
		return activationWidth;
	}

	@Override
	public NPCFightingAgent getNPCFightingAgent() {
		// TODO Auto-generated method stub
		return fightingAgent;
	}

}