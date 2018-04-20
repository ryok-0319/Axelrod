/**
 *
 */
package axelrod;

import java.util.Arrays;
/**
 * @author ryo
 *
 */

public class Main {
	/**
	 * @param args
	 */
	// エージェントの数
	public static final int N = 20;

	// 世代数
	public static final int T = 100;

	public static void main(String[] args) {
		// エージェントの配列を生成
		Agent[] agents = new Agent[N];
		for (int i = 0; i < N; i++) {
			agents[i] = new Agent();
			agents[i].setAgentNumber(i);
		}

		// T世代のシミュレーションを行う
		for (int k = 0; k < T; k++) {
			Trial.oneGeneration(agents);
			// エージェントを得点の高い順に並び替え
			Arrays.sort(agents, (a,b)-> b.score - a.score );
			System.out.println(k + "世代の結果");
			for(int n = 0; n < N; n++) {
				System.out.println("エージェント" + agents[n].num + "番：" + agents[n].score);
				System.out.println("大胆さ：" + agents[n].B + "懲罰率：" + agents[n].V);
			}
			Evolution.evolution(agents);
		}
	}
}
