/**
 *
 */
package axelrod;

import java.util.Random;
/**
 * @author ryo
 *
 */
class Trial {
	static void game (Agent agents[], int firstAgentNum, int secondAgentNum, int thirdAgentNum) {
		// 裏切りが目撃される確率s
		double s = Math.random();
		// 一人目が行動を決定
		agents[firstAgentNum].makeStrategy(s);
		// 二人目が一人目の裏切りに気がついた場合
		if (agents[firstAgentNum].strategy == 0 && s > Math.random()) {
			agents[secondAgentNum].punishTraitor(firstAgentNum, s);
			// 三人目が二人目の裏切りに気がついた場合
			if (agents[secondAgentNum].strategy == 0 && s > Math.random()) {
				agents[thirdAgentNum].punishIgnorer(secondAgentNum, s);
			}
		}
		System.out.println("1人目：" + firstAgentNum);
		System.out.println("2人目：" + secondAgentNum);
		System.out.println("3人目：" + thirdAgentNum);
	}
}



public class Main {
	/**
	 * @param args
	 */
	// エージェントの数
	public static final int N = 20;

	public static void main(String[] args) {
		Agent[] agents = new Agent[N];
		for (int i = 0; i < N; i++) {
			agents[i] = new Agent();
			agents[i].setAgentNumber(i);
		}

		// 動作確認
		Random rnd = new Random();
		for(int i = 0; i < 4; i++) {
			for (int firstAgentNum = 0; firstAgentNum < N; firstAgentNum++) {
				// 二人目をランダムに選出
				int secondAgentNum = rnd.nextInt(N);
				// 一人目と二人目がカブらないようにする
				while(secondAgentNum == firstAgentNum) {
					secondAgentNum = rnd.nextInt(N);
				}
				// 三人目をランダムに選出
				int thirdAgentNum = rnd.nextInt(N);
				// 三人目と、他の二人がカブらないようにする
				while(thirdAgentNum == firstAgentNum || thirdAgentNum == secondAgentNum) {
					thirdAgentNum = rnd.nextInt(N);
				}
				Trial.game(agents, firstAgentNum, secondAgentNum, thirdAgentNum);
			}
		}
		for(int i = 0; i < N; i++) {
			System.out.println(Agent.scores[i]);
		}
	}
}
