package axelrod;

public class Agent {
	// 協力するか裏切るかの戦略
	int strategy;

	int num;

	int score;

	static int T = 0;
	static int H = 0;
	static int F = -3;
	static int M = 1;
	static int E = 0;
	static int P = 0;
	static int C = -2;
	static int R = 9;
	static int E_d = 0;
	static int P_d = 0;
	static int C_d = 0;
	static int R_d = 0;
	static int E_dd = 0;
	static int P_dd = 0;
	static int C_dd = -2;
	static int R_dd = 9;

	// 大胆さ
	double B = Math.random();
	// 裏切り者への懲罰率
	double V = Math.random();
	// 協調者に報酬を与える確率
	double L = Math.random();

	// コンストラクタ
	Agent() {
		// 協力(1)、裏切り(0)だが、とりあえず2にセット
		this.strategy = 2;
	}

	// エージェントの番号をセット
	void setAgentNumber(int i) {
		this.num = i;
	}

	// 裏切りか協力かを決定
	void makeStrategy(double s, Agent agents[]) {
		// 協力
		if(s < this.B) {
			this.strategy = 1;
			this.getBenefit(F);
			this.giveBenefitToOthers(M, agents);
		}
		// 裏切り
		else {
			this.strategy = 0;
			this.getBenefit(T);
			this.giveBenefitToOthers(H, agents);
		}
	}

	// 裏切り者への懲罰
	void punishTraitor(int target_num, int cost, int punishment, Agent agents[]) {
		// 協力（懲罰）する
		if (this.V > Math.random()) {
			this.strategy = 1;
			this.getBenefit(cost);
			this.giveBenefitToSomeone(punishment, target_num, agents);
		}
		// 裏切る（見逃す）
		else {
			this.strategy = 0;
			// 見逃した場合は何も発生しない
		}
	}

	void rewardCooperator(int target_num, int cost, int reward, Agent agents[]) {
		// 協力する（報酬を与える）
		if(this.L > Math.random()) {
			this.strategy = 1;
			this.getBenefit(cost);
			this.giveBenefitToSomeone(reward, target_num, agents);
		}
		// 裏切る（報酬を与えない）
		else {
			this.strategy = 0;
			// 何も発生しない
		}
	}

	// 報酬を得る（またはコストを払う）
	void getBenefit(int benefit) {
		this.score += benefit;
	}

	// 自分以外のエージェントに報酬を与える
	void giveBenefitToOthers(int benefit, Agent agents[]) {
		// 自分から一旦引き算をしておく
		this.score -= benefit;
		for(int i = 0; i < agents.length; i++) {
			agents[i].score += benefit;
		}
	}

	// 特定のエージェント一人に報酬または懲罰を与える
	void giveBenefitToSomeone(int benefit, int traitor_num, Agent agents[]) {
		agents[traitor_num].score += benefit;
	}
}
