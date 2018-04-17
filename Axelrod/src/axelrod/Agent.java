package axelrod;

public class Agent {
	// 協力するか裏切るかの戦略
	int strategy;

	int num;

	static int scores[] = new int[20];

	static int T = 3;
	static int H = -1;
	static int E = -2;
	static int P = -9;
	static int E_d = -2;
	static int P_d = -9;

	// 協調率
	double B_i = Math.random();
	// 裏切り者への懲罰率
	double V_j = Math.random();
	// 裏切りを見逃した者への懲罰率
	double V_k = Math.random();

	// コンストラクタ
	Agent() {
		// 初期値は、協力(1)か、裏切り(0)を1/2で選択
		this.strategy = 2;
	}

	// エージェントの番号をセット
	void setAgentNumber(int i) {
		this.num = i;
	}

	// 裏切りか協力かを決定
	void makeStrategy(double s) {
		// 協力
		if(s > this.B_i) {
			this.strategy = 1;
			// 協力の場合は何も発生しない
		}
		// 裏切り
		else {
			this.strategy = 0;
			this.getBenefit(T);
			this.giveBenefitToOthers(H);
		}
	}

	// 裏切り者への懲罰
	void punishTraitor(int agent_num, double s) {
		// 協力（懲罰）する
		if (s > this.V_j) {
			this.strategy = 1;
			this.getBenefit(E);
			this.giveBenefitToTraitor(P, agent_num);
		}
		// 裏切る（見逃す）
		else {
			this.strategy = 0;
			// 見逃した場合は何も発生しない
		}
	}
	// 裏切り者を無視したものへの懲罰
	void punishIgnorer(int agent_num, double s) {
		// 協力（懲罰）する
		if (s > this.V_k) {
			this.strategy = 1;
			this.getBenefit(E_d);
			this.giveBenefitToTraitor(P_d, agent_num);
		}
		// 裏切る（見逃す）
		else {
			this.strategy = 0;
			// 見逃した場合は何も発生しない
		}
	}

	void evolution(int B_i, int V_j) {
		// 突然変異
		if (Math.random() < 0.06) {
			this.B_i = Math.random();
			this.V_j = Math.random();
		}
		// 通常の進化
		else {
			this.B_i = B_i;
			this.V_j = V_j;
		}
	}

	// 報酬を得る（またはコストを払う）
	void getBenefit(int benefit) {
		scores[this.num] += benefit;
	}

	// 自分以外のエージェントに報酬を与える
	void giveBenefitToOthers(int benefit) {
		// 自分から一旦引き算をしておく
		scores[this.num] -= benefit;
		for(int i = 0; i < scores.length; i++) {
			scores[i] += benefit;
		}
	}

	// 非協力者を攻撃
	void giveBenefitToTraitor(int benefit, int traitor_num) {
		scores[traitor_num] += benefit;
	}
}
