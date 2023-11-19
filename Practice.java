class process {
    int id, at, bt, ct, tat, wt, priority, rt;

    public process(int id, int at, int bt){
        this.id = id;
        this.at = at;
        this.bt = bt;
        this.rt = bt;
    }

    public process(int id, int at, int bt, int priority){
        this.id = id;
        this.at = at;
        this.bt = bt;
        this.priority = priority;
    }
}

class fcfs{
    process parr[];

    public fcfs(process parr[]){
        this.parr = parr;
    }

    void execute(){
        int completiontime = 0;
        double avgtat = 0;
        double avgwt = 0;

        System.out.println("ID" + "\t" + "AT" + "\t" + "BT" + "\t" + "CT" + "\t" + "TAT" + "\t" + "WT");

        for(process p : parr){
            if(completiontime < p.at){
                completiontime = p.at;
            }
             completiontime = completiontime + p.bt;

             p.ct = completiontime;
             p.tat = p.ct - p.at;
             p.wt = p.tat - p.bt;
             avgtat = p.tat + avgtat;
             avgwt = p.wt + avgwt;


        }
    }
}


class Round_robin{
    process p[];
    int tq;
    double avgtat = 0;
    double avgwt = 0;

    public Round_robin(int tq, process p[]){
        this.p = p;
        this.tq = tq;
    }

    public void execute(){
        int n = p.length;
        int[] remainingTime = new int[n];
        
        for (int i = 0; i < n; i++) {
            remainingTime[i] = p[i].bt;
        }

        int t = 0;
        while(true){
            boolean done = true;

            for (int i = 0; i < n; i++) {
                if(remainingTime[i] > 0){
                    done = false;

                    if (remainingTime[i] > tq) {
                        t+=tq;
                        remainingTime[i] -= tq;
                    }else{
                        t = t + remainingTime[i];
                        p[i].wt = t - p[i].bt;
                        remainingTime[i] = 0;
                        p[i].wt = t;
                        
                    }
                }
            }
        }
    }
}




public class Practice {
    public static void main(String[] args){
        System.out.println("Scheduling Practical");
    }
}
