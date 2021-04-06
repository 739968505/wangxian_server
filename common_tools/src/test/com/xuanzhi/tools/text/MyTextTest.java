package com.xuanzhi.tools.text;

import junit.framework.TestCase;

public class MyTextTest extends TestCase{

	public void testA(){
		MyText mt = new MyText();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<f>���</f><F color='#ffffff'>hehe</F>\n");
		sb.append("<f>�������</f>\t<F color='#ffffff'>hehe2</F>\n");
		sb.append("<i id='0'></i>\t<F color='#ffffff'>hehe3</F>\n");
		mt.setText(sb.toString());
		
		assertEquals(11,mt.elements.size());
		MyText.TextItem it = (MyText.TextItem)mt.elements.get(0);
		assertEquals("���",it.text);
		
		it = (MyText.TextItem)mt.elements.get(3);
		assertEquals("�������",it.text);
		
		it = (MyText.TextItem)mt.elements.get(9);
		assertEquals("hehe3",it.text);
		
		
	}
	
	
	public void testB(){
		MyText mt = new MyText();
		
		StringBuffer sb = new StringBuffer();
		sb.append("�����迪�����������˿�\n");
		sb.append("<f>�������</f>\t���÷�����������<F color='#ffffff'>hehe2</F>\n");
		sb.append("<i id='0' width='1234' height   =   '234'></i>��ʱ����������˹���������Ƿ�<F color ='#ffff23' size='25'>hehe3</F>\n");
		sb.append("��ɳ������������ɱ�뿪�Է�");
		mt.setText(sb.toString());
		
		assertEquals(12,mt.elements.size());
		MyText.TextItem it = (MyText.TextItem)mt.elements.get(0);
		assertEquals("�����迪�����������˿�",it.text);
		
		MyText.TextItem it1 = (MyText.TextItem)mt.elements.get(9);
		assertEquals("hehe3",it1.text);
		assertEquals(0xffff23,it1.color);
		assertEquals(25,it1.size);
		
		MyText.BR b = (MyText.BR)mt.elements.get(3);
		assertEquals(MyText.BR.TAB,b);
		
		it = (MyText.TextItem)mt.elements.get(11);
		assertEquals("��ɳ������������ɱ�뿪�Է�",it.text);
		
		MyText.ImageItem it2 = (MyText.ImageItem)mt.elements.get(7);
		assertEquals(1234,it2.width);
		assertEquals(234,it2.height);
		assertEquals(0,it2.id);
		
		
		
		
		
	}
}
