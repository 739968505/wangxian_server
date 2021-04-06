//û�г�������true����������false
function CheckLength(strInput,varMaxLength)
{       
	//�ַ�����ʵ�ʳ���
	var varRealLength = 0;
	
	//�ַ���������С��λ,Ӣ���ַ���ռλ��
	var varCount = 1;
	
	//�����ַ���
	var strRegx = "/[\u4e00-\u9fa5]/"; 

	//����ַ�У��
	for (var i = 0; i < strInput.length; i++) 
	{
		re = eval(strRegx);
		if(re.test(strInput.charAt(i)) == true)                               
		{
			//��������ģ���ռλ��Ϊ2
			varCount = 2;
		}
		else
		{
			//�����Ӣ�ģ���ռλ��Ϊ1
			varCount = 1;
		}
		varRealLength += varCount;
	}
	//alert(varRealLength);
	//�ж�
	if(varMaxLength >= varRealLength)
	{
		return true;
	}
	else
	{
		return false;
	}           
}

//�ж����������Ƿ�ȫ�����ǿո�
//ȫ�����ǿո񷵻�true����ȫ�Ƿ���false
function CheckBlank(strInput)
{		
	var re = / /g;
	var resultStr = strInput.replace(re,"");
	
	if(resultStr == "")
	{
		return true;
	}
	else
	{
		return false;
	}
}

//�ж��Ƿ�Ϊ���ͣ�����bool
function IsInteger(string ,sign) 
{  
	var integer; 
	if ((sign!=null) && (sign!='-') && (sign!='+')) 
	{ 
		alert('IsInter(string,sign)�Ĳ�������nsignΪnull��"-"��"+"'); 
		return false; 
	} 
	integer = parseInt(string); 
	if (isNaN(integer)) 
	{ 
		return false; 
	} 
	else if (integer.toString().length==string.length) 
	{  
		if ((sign==null) || (sign=='-' && integer<0) || (sign=='+' && integer>0)) 
		{ 
			return true; 
		} 
		else 
			return false;  
	} 
	else 
		return false; 
}

//������(����: 2003-12-18)������bool
function IsDate(DateString) 
{ 
	if (DateString==null) return false; 
	var Dilimeter = '-'; 
	var tempy=''; 
	var tempm=''; 
	var tempd=''; 
	var tempArray; 
	if (DateString.length<8 && DateString.length>10) 
		return false;    
	tempArray = DateString.split(Dilimeter); 
	if (tempArray.length!=3) 
		return false; 
	if (tempArray[0].length==4) 
	{ 
		tempy = tempArray[0]; 
		tempm = tempArray[1]; 
		tempd = tempArray[2]; 
	} 
	else 
	{ 
		return false; 
	} 
	if(!IsInteger(tempy) || !IsInteger(tempm) || !IsInteger(tempd))
		return false; 
	
	var tDateString = tempy + '/'+tempm + '/'+tempd+' 8:0:0';//�Ӱ�Сʱ����Ϊ���Ǵ��ڶ����� 
	var tempDate = new Date(tDateString); 
	if (isNaN(tempDate)) 
	{
		return false; 
	}
	if (((tempDate.getUTCFullYear()).toString()==tempy) && (tempDate.getMonth()==parseInt(tempm)-1) && (tempDate.getDate()==parseInt(tempd))) 
	{ 
		return true; 
	} 
	else 
	{ 
		return false; 
	} 
}
//�Ƚ����������ַ���,endTime ���ڵ��� beginTime ����true; ���򷵻�false;
function CompareTime(beginDate,endDate)
{
	var tBeginDate = beginDate.replace("-","/")+' 8:0:0';//�Ӱ�Сʱ����Ϊ���Ǵ��ڶ�����
	tBeginDate = tBeginDate.replace("-","/");	
	var tEndDate = endDate.replace("-","/")+' 8:0:0';
	tEndDate = tEndDate.replace("-","/");
	
	var tbegin = new Date(tBeginDate); 
	var tend = new Date(tEndDate); 
	if (isNaN(tbegin) || isNaN(tend)) 
	{
		return false; 
	}
	else
	{
		if(tend < tbegin)
			return false;
	}
	return true;
}

//�ж�ͼƬ�ļ������Ƿ�Ϊgif��jpg������bool
function CheckPicFile(url) 
{  
	var extType = url.substring(url.indexOf(".")+1,url.length);
	extType = extType.toLowerCase();
	if(extType != "gif" && extType != "jpg")
	{
		return true;
	}
	else
	{
		return false;
	}
}

//�ж�ͼƬ�ļ������Ƿ�Ϊgif��jpg��png������bool
function CheckImgFile(url) 
{  
	var extType = url.substring(url.lastIndexOf(".")+1,url.length);
	extType = extType.toLowerCase();
	if(extType != "gif" && extType != "jpg" && extType != "png")
	{
		return true;
	}
	else
	{
		return false;
	}
}
//�ж�ͼƬ�ļ������Ƿ�Ϊgif��jpg  +  Flash�ļ������Ƿ�swf������bool
function CheckPicAddFlashFile(url) 
{  
	var extType = url.substring(url.indexOf(".")+1,url.length);
	extType = extType.toLowerCase();
	if(extType != "gif" && extType != "jpg" && extType != "swf")
	{
		return true;
	}
	else
	{
		return false;
	}
}

//�ж�Ӱ���ļ������Ƿ�Ϊmp3��wma��wmv��asf��rm��rmb��mpg��avi��swf������bool
function CheckMediaFile(url) 
{  
	var extType = url.substring(url.lastIndexOf(".")+1,url.length);
	extType = extType.toLowerCase();
	if(extType != "mp3" && extType != "wma" && extType != "wmv" && extType != "asf" && extType != "rm" && extType != "rmb" && extType != "mpg" && extType != "avi" && extType != "swf")
	{
		return true;
	}
	else
	{
		return false;
	}
}
//�ж���Ƶ�ļ������Ƿ�Ϊwmv��asf��rm��rmvb��mpg��avi������bool
function CheckVideoFile(url) 
{  
	var extType = url.substring(url.lastIndexOf(".")+1,url.length);
	extType = extType.toLowerCase();
	if(extType != "wmv" && extType != "asf" && extType != "rm" && extType != "rmvb" && extType != "mpg" && extType != "avi")
	{
		return true;
	}
	else
	{
		return false;
	}
}

// �жϵ����ʼ��Ƿ��ʽ��ȷ

function is_email(object_name)
{
	var string;
	string=new String(object_name);
	var len=string.length;
	
	if(len==0)
	{
		return true;
	}
	else
	{			
		if (string.indexOf("@",1)==-1||string.indexOf(".",1)==-1||string.length<7)
		{
			return false;
		}

		if (string.charAt(len-1)=="."||string.charAt(len-1)=="@")
		{
			return false;
		}
		return true;
	}
}

//�ж��Ƿ�Ϊ���ģ��Ƿ���true,���򷵻�false
function IsChinese(strInput)
{
	//�����ַ���
	var strRegx = "/[\u4e00-\u9fa5]/"; 
	//����ַ�У��
	for (var i = 0; i < strInput.length; i++) 
	{
		re = eval(strRegx);
		if(re.test(strInput.charAt(i)) == true)
		{
			return true;
		}
	}
	
	return false
}

//ȥ��ո�;
function LTrim(s){
    return s.replace( /^\\s*/, "");
}
//ȥ�ҿո�;
function RTrim(s){
    return s.replace( /\\s*$/, "");
}
//ȥ���ҿո�;
function Trim(s){
    return RTrim(LTrim(s));
}
function IsMail(strInput)//email����Ϊ�ջ��ʽ����ȷ��
{
	var tmp_str = Trim(strInput);
	var patrn=/^([a-zA-Z0-9._-]+)@([a-zA-Z0-9_-]+)(\.[a-zA-Z0-9_-]+)(\.*[a-zA-Z0-9_-]*)$/; 
	if(!patrn.exec(tmp_str)){
		return false;
	}	
	return true;
}

//�ֻ��Ų���Ϊ�ջ��ʽ����ȷ
function IsMobile(strInput)//(��13��ͷ����15��ͷ��11����)
{
	var tmp_str = Trim(strInput);
	//var patrn=/^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/;//(��13��ͷ��11����)
	//var patrn=/^13\d{9}|159\d{8}|153\d{8}$/;//(��13��ͷ����159��ͷ����153��ͷ��11����)
	var patrn=/^(13|15)\d{9}$/;//(��13��ͷ����15��ͷ��11����)
	if(tmp_str.length != 11)
		return false;
	
	if(!patrn.exec(tmp_str)){
		return false;
	}else{
		return true;
	}
}

//�Ƿ�ΪӢ���ַ���BEGIN
function IsCharsInBagEn (s, bag) 
{ 
	var i,c; 
	for (i = 0; i < s.length; i++) 
	{ 
		c = s.charAt(i);//�ַ���s�е��ַ� 
		if (bag.indexOf(c) <0) 
		return c; 
	} 
	return ""; 
} 
function IsEnglish(s) 
{ 
	var errorChar; 
	var badChar = " ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; 
	errorChar = IsCharsInBagEn( s, badChar) 
	if (errorChar != "" ) 
		return false; 
		
	return true; 
} 
//�Ƿ�ΪӢ���ַ���END

//�û������û����ַ��Ƿ���ʣ�ֻ��������ĸ�����֡��»���
function IsCharacterTrue(txtUserName)
{
	var checkOKM = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_";
	var checkStrM = txtUserName;
	var allValidM = true;
	for (i = 0;  i < checkStrM.length;  i++)
	{
		ch = checkStrM.charAt(i);
		for (j = 0;  j < checkOKM.length;  j++)
		if (ch == checkOKM.charAt(j))
			break;
		if (j == checkOKM.length)
		{
			allValidM = false;
			break;
		}
	}
	return allValidM;
}