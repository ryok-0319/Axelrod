/**
 *
 */
package axelrod;

import java.util.Arrays;
/**
 * @author ryo
 *
 */
class Trial {
	static void oneGeneration (Agent agents[]) {
		// 得点のリセット
		for(int k = 0; k < agents.length; k++) {
			agents[k].score = 0;
		}
		// 一人当たり、4回意思決定をする
		for (int i = 0; i < 4; i++) {
			// 最初の意思決定者は0番から順番に選ぶ
			for (int firstAgentNum = 0; firstAgentNum < agents.length; firstAgentNum++) {
				// 裏切りが目撃される確率s
				double s = Math.random();
				// 意思決定者が行動を決定
				agents[firstAgentNum].makeStrategy(s, agents);
				// 意思決定者が裏切った場合
				if (agents[firstAgentNum].strategy == 0) {
					// 他のエージェント全員が懲罰の機会を持つ
					for(int punisherNum = 0; punisherNum < agents.length; punisherNum++) {
						// 意思決定者本人は除外する
						if(punisherNum == firstAgentNum) {
							continue;
						}
						// 裏切りを発見した場合
						if(s > Math.random()) {
							agents[punisherNum].punishTraitor(firstAgentNum, agents);
							// 発見者が懲罰しなかった（裏切った）場合
							if(agents[punisherNum].strategy == 0) {
								// 他のエージェント全員が懲罰の機会を持つ
								for(int metaPunisherNum = 0; metaPunisherNum < agents.length; metaPunisherNum++) {
									// 意思決定者と発見者は除外する
									if(metaPunisherNum == firstAgentNum || metaPunisherNum == punisherNum) {
										continue;
									}
									// 懲罰しなかったのを発見した場合
									if(s > Math.random()) {
										agents[metaPunisherNum].punishIgnorer(punisherNum, agents);
									}
								}
							}
						}
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

	// 世代数
	public static final int T = 100;

	public static void main(String[] args) {
		Agent[] agents = new Agent[N];
		for (int i = 0; i < N; i++) {
			agents[i] = new Agent();
			agents[i].setAgentNumber(i);
		}

		// T世代行う
		for (int k = 0; k < T; k++) {
			Trial.oneGeneration(agents);
			// エージェントを得点の高い順に並び替え
			Arrays.sort(agents, (a,b)-> b.score - a.score );
			System.out.println(k + "世代の結果");
			for(int n = 0; n < N; n++) {
				System.out.println("エージェント" + agents[n].num + "番：" + agents[n].score);
				System.out.println("大胆さ：" + agents[n].B + "懲罰率：" + agents[n].V);
			}
			// 次の世代に向け、得点順の下位5人は上位5人のパラメータで上書きする
			for(int i = 0; i < 5; i++) {
				agents[i+15].B = agents[i].B;
				agents[i+15].V = agents[i].V;
			}
			// 突然変異
			for(int i = 0; i < N; i++) {
				if(Math.random() < 0.06) {
					agents[i].B = Math.random();
				}
				if(Math.random() < 0.06) {
					agents[i].V = Math.random();
				}
			}
		}
	}
}
