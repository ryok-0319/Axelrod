package axelrod;

public class Evolution {
	static void evolution (Agent agents[]) {
		// 得点順の下位5人は上位5人のパラメータで上書きする
		for(int i = 0; i < 5; i++) {
			agents[i+15].B = agents[i].B;
			agents[i+15].V = agents[i].V;
			agents[i+15].L = agents[i].L;
		}
		// 突然変異
		for(int i = 0; i < agents.length; i++) {
			if(Math.random() < 0.06) {
				agents[i].B = Math.random();
			}
			if(Math.random() < 0.06) {
				agents[i].V = Math.random();
			}
			if(Math.random() < 0.06) {
				agents[i].L = Math.random();
			}
		}
	}
}
