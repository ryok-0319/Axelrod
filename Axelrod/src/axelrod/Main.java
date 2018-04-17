/**
 *
 */
package axelrod;

import java.util.Arrays;
import java.util.Random;
/**
 * @author ryo
 *
 */
class Trial {
	static void oneGeneration (Agent agents[]) {
		// 裏切りが目撃される確率s
		double s = Math.random();
		Random rnd = new Random();
		// 一人当たり、4回意思決定をする
		for (int i = 0; i < 4; i++) {
			// 意思決定者は0番から順番に選ぶ
			for (int firstAgentNum = 0; firstAgentNum < agents.length; firstAgentNum++) {
				// 二人目をランダムに選出
				int secondAgentNum = rnd.nextInt(agents.length);
				// 一人目と二人目がカブらないようにする
				while(secondAgentNum == firstAgentNum) {
					secondAgentNum = rnd.nextInt(agents.length);
				}
				// 三人目をランダムに選出
				int thirdAgentNum = rnd.nextInt(agents.length);
				// 三人目と、他の二人がカブらないようにする
				while(thirdAgentNum == firstAgentNum || thirdAgentNum == secondAgentNum) {
					thirdAgentNum = rnd.nextInt(agents.length);
				}
				// 一人目が行動を決定
				agents[firstAgentNum].makeStrategy(s, agents);
				// 二人目が一人目の裏切りに気がついた場合
				if (agents[firstAgentNum].strategy == 0 && s > Math.random()) {
					agents[secondAgentNum].punishTraitor(firstAgentNum, s, agents);
					// 三人目が二人目の裏切りに気がついた場合
					if (agents[secondAgentNum].strategy == 0 && s > Math.random()) {
						agents[thirdAgentNum].punishIgnorer(secondAgentNum, s, agents);
					}
				}
			}
		}
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

		// 4ラウンド行う
		Trial.oneGeneration(agents);

		// エージェントを得点の高い順に並び替え
		Arrays.sort(agents, (a,b)-> b.score - a.score );
		for(int i = 0; i < N; i++) {
			System.out.println("エージェント" + agents[i].num + "番：" + agents[i].score);
		}
	}
}
