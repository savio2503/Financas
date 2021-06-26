package code;

import tools.SQLUtil;
import window.AddAccount;
import window.AddCard;
import window.AddCost;
import window.Main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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
