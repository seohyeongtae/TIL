package com.test;

import java.util.Random;

public class array {

	public static void main(String[] args) throws InterruptedException {
		double[] test = new double[2];
		Random random = new Random();
		
		int i = 0;
		while(true) {
			double res = random.nextDouble();
			
			Thread.sleep(2000);
			//append�� ���� ����. �� �ؿ� �������� ���ϴ� ���� �ϸ� ��.
			test[1] = res;
			System.out.println(test[0]+" "+test[1]);
			
			//���� ������ ���� �غ�
			double res1 = test[0];
			test[0] = res;
			test[1] = res1;
			
			System.out.println(res);
			i ++;
		}

	}

}
