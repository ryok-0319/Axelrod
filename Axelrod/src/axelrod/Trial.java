package axelrod;

public class Trial {
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
							agents[punisherNum].punishTraitor(firstAgentNum, Agent.E, Agent.P, agents);
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
										agents[metaPunisherNum].punishTraitor(punisherNum, Agent.E_d, Agent.P_d, agents);
									}
								}
							}
							// 発見者が懲罰した（協力した）場合
							else {
								// 他のエージェント全員が報酬を与える機会を持つ
								for(int rewarderNum = 0; rewarderNum < agents.length; rewarderNum++) {
									// 意思決定者と発見者は除外する
									if(rewarderNum == firstAgentNum || rewarderNum == punisherNum) {
										continue;
									}
									// 懲罰しているのを発見した場合
									if(s > Math.random()) {
										agents[rewarderNum].rewardCooperator(punisherNum, Agent.C_d, Agent.R_d, agents);
									}
								}
							}
						}
					}
				}
				// 意思決定者が協力した場合
				if (agents[firstAgentNum].strategy == 1) {
					// 他のエージェント全員が報酬を与える機会を持つ
					for(int rewarderNum = 0; rewarderNum < agents.length; rewarderNum++) {
						// 意思決定者本人は除外する
						if(rewarderNum == firstAgentNum) {
							continue;
						}
						// 意思決定者の協調行動を発見した場合
						if(s > Math.random()) {
							agents[rewarderNum].rewardCooperator(firstAgentNum, Agent.C, Agent.R, agents);
							// 発見者が報酬を与えなかった（裏切った）場合
							if(agents[rewarderNum].strategy == 0) {
								// 他のエージェント全員が懲罰の機会を持つ
								for(int punisherNum = 0; punisherNum < agents.length; punisherNum++) {
									// 意思決定者と発見者は除外する
									if(punisherNum == firstAgentNum || punisherNum == rewarderNum) {
										continue;
									}
									// 懲罰しなかったのを発見した場合
									if(s > Math.random()) {
										agents[punisherNum].punishTraitor(rewarderNum, Agent.E_dd, Agent.P_dd, agents);
									}
								}
							}
							// 発見者が報酬を与えた（協力した）場合
							else {
								// 他のエージェント全員が報酬を与える機会を持つ
								for(int metaRewarderNum = 0; metaRewarderNum < agents.length; metaRewarderNum++) {
									// 意思決定者と発見者は除外する
									if(metaRewarderNum == firstAgentNum || metaRewarderNum == rewarderNum) {
										continue;
									}
									// 報酬を与えているのを発見した場合
									if(s > Math.random()) {
										agents[metaRewarderNum].rewardCooperator(rewarderNum, Agent.C_dd, Agent.R_dd, agents);
									}
								}
							}
						}
					}
				}
			}
		}
		double sumB = 0;
		double sumL = 0;
		for(int i = 0; i < agents.length; i++) {
			sumB += agents[i].B;
			sumL += agents[i].L;
		}
		//System.out.println(sumB / agents.length);
		System.out.println(sumL / agents.length);
	}
}
