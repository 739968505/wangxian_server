package com.fy.engineserver.menu.sifang;

import com.fy.engineserver.core.Game;
import com.fy.engineserver.menu.Option;
import com.fy.engineserver.sifang.SiFangManager;
import com.fy.engineserver.sprite.Player;

//NPC点击参加报名
public class Option_SiFang_Join extends Option{

	private int siFangType;
	public int getSiFangType() {
		return siFangType;
	}

	public void setSiFangType(int siFangType) {
		this.siFangType = siFangType;
	}
	
	public Option_SiFang_Join() {
		super();
	}

	public Option_SiFang_Join(int type) {
		super();
		this.siFangType = type;
	}
	
	public byte getOType() {
		return Option.OPTION_TYPE_SERVER_FUNCTION;
	}

	@Override
	public void doSelect(Game game, Player player) {
		SiFangManager.getInstance().opt_BaoMing(player, siFangType);
		
	}
}