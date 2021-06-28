package code;

import window.Main;

import tools.SQLUtil;

public class Financas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("teste");
		SQLUtil.setup();
		System.out.println("finish");
		try {
			Main frame = new Main();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
