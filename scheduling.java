import java.util.Scanner;

class process {
  int id;
  int at;
  int bt;
  int ct;
  int tat;
  int wt;
  int priority;
  int rt;

  public process(int id, int at, int bt) {
    this.id = id;
    this.at = at;
    this.bt = bt;
    this.rt = bt;
  }

  public process(int id, int at, int bt, int priority) {
    this.id = id;
    this.at = at;
    this.bt = bt;
    this.priority = priority;
  }
}

class fcfs {
  process parr[];

  public fcfs(process parr[]) {
    this.parr = parr;
  }

  void execute() {
    int completiontime = 0;
    double avgtat = 0;
    double avgwt = 0;
    System.out.println("--------fcfs--------");
    System.out.println("ID" + "\t" + "AT" + "\t" + "BT" + "\t" + "CT" + "\t" + "TAT" + "\t" + "WT");

    for (process p : parr) {
      if (completiontime < p.at) {
        completiontime = p.at;
      }
      completiontime = completiontime + p.bt;
      p.ct = completiontime;
      p.tat = p.ct - p.at;
      p.wt = p.tat - p.bt;
      avgtat = p.tat + avgtat;
      avgwt = p.wt + avgwt;

      System.out.println(
        p.id +
        "\t" +
        p.at +
        "\t" +
        p.bt +
        "\t" +
        p.ct +
        "\t" +
        p.tat +
        "\t" +
        p.wt
      );
    }
    System.out.println("Average turn around time: " + avgtat / parr.length);
    System.out.println();
    System.out.println("Average waiting time: " + avgwt / parr.length);
  }
}

class priority {
  process parr[];
  process temp;
  double avgtat = 0;
  double avgwt = 0;

  public priority(process parr[]) {
    this.parr = parr;
  }

  void execute() {
    int completiontime = 0;
    double avgtat = 0;
    double avgwt = 0;
    // sort according to priority
    for (int i = 0; i < parr.length; i++) {
      for (int j = i + 1; j < parr.length; j++) {
        if (parr[i].priority < parr[j].priority) {
          temp = parr[i];
          parr[i] = parr[j];
          parr[j] = temp;
        }
      }
    }
    System.out.println("--------priority--------");
    System.out.println(
      "id" +
      "\t" +
      "priority" +
      "\t" +
      "at" +
      "\t" +
      "bt" +
      "\t" +
      "ct" +
      "\t" +
      "tat" +
      "\t" +
      "wt"
    );

    for (process p : parr) {
      if (completiontime < p.at) {
        completiontime = p.at;
      }
      completiontime = p.bt + completiontime;
      p.ct = completiontime;
      p.tat = p.ct - p.at;
      p.wt = p.tat - p.bt;
      avgtat = avgtat + p.tat;
      avgwt = avgwt + p.wt;
      System.out.println(
        p.id +
        "\t" +
        p.priority +
        "\t" +
        p.at +
        "\t" +
        p.bt +
        "\t" +
        p.ct +
        "\t" +
        p.tat +
        "\t" +
        p.wt
      );
    }
    System.out.println("Average turn around time: " + avgtat / parr.length);
    System.out.println("Average waiting time: " + avgwt / parr.length);
  }
}

class sjf {
  process processes[];
  double avgtat = 0;
  double avgwt = 0;

  public sjf(process processes[]) {
    this.processes = processes;
  }

  void execute() {
    int n = processes.length;
    boolean executed[] = new boolean[n];
    double avgtat = 0, avgwt = 0;
    int completed = 0, time = 0;
    while (completed != n) {
      int index = -1;
      int min = 500;
      for (int i = 0; i < n; i++) {
        if (processes[i].at <= time && !executed[i] && processes[i].rt < min) {
          min = processes[i].rt;
          index = i;
        }
      }
      if (index != -1) {
        processes[index].rt--;
        time++;
        if (processes[index].rt == 0) {
          completed++;

          executed[index] = true;

          // Assigning values to process object
          processes[index].ct = time;

          processes[index].tat = processes[index].ct - processes[index].at;

          processes[index].wt = processes[index].tat - processes[index].bt;

          avgtat += processes[index].tat;

          avgwt += processes[index].wt;
        }
      } else {
        time++;
      }
    } // end of while
    System.out.println("--------sjf--------");
    System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");

    for (process p : processes) {
      int tat = p.ct - p.at;

      int wt = tat - p.bt;

      System.out.println(
        p.id + "\t" + p.at + "\t" + p.bt + "\t" + p.ct + "\t" + tat + "\t" + wt
      );
    }
    System.out.println("Average turnaround time: " + avgtat / n);

    System.out.println("Average waiting time: " + avgwt / n);
  }
}

class Round_robin {
  process p[];
  int tq;
  double avgtat = 0;
  double avgwt = 0;

  public Round_robin(process p[], int tq) {
    this.p = p;
    this.tq = tq;
  }

  public void execute() {
    int n = p.length;
    int[] remainingTime = new int[n];
    // double avgtat = 0, avgwt = 0;

    for (int i = 0; i < n; i++) remainingTime[i] = p[i].bt;

    int t = 0;
    while (true) {
      boolean done = true;

      for (int i = 0; i < n; i++) {
        if (remainingTime[i] > 0) {
          done = false;

          if (remainingTime[i] > tq) {
            t += tq;
            remainingTime[i] -= tq;
          } else {
            t = t + remainingTime[i];
            p[i].wt = t - p[i].bt;
            remainingTime[i] = 0;
            p[i].ct = t;
            p[i].tat = p[i].ct - p[i].at;
            avgtat += p[i].wt;
            avgwt += p[i].tat;
          }
        }
      }

      if (done == true) break;
    }
    System.out.println("--------Round_robin--------");
    System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");
    for (process p : p) {
      System.out.println(
        p.id +
        "\t" +
        p.at +
        "\t" +
        p.bt +
        "\t" +
        p.ct +
        "\t" +
        p.tat +
        "\t" +
        p.wt
      );
    }
    System.out.println("Average turn around time: " + avgtat / n);
    System.out.println("Average waiting time: " + avgwt / n);
  }
}

class scheduling {

  public static void main(String[] args) {
    try (Scanner sc = new Scanner(System.in)) {
      System.out.println("Enter total no. of process: ");
      int n = sc.nextInt();
      process p[] = new process[n];
      process p1[] = new process[n];
      process p2[] = new process[n];
      process p3[] = new process[n];
      for (int i = 0; i < n; i++) {
        System.out.println("Enter details for process " + (i + 1));
        System.out.println("Enter arrival time ");
        int at = sc.nextInt();
        System.out.println("Enter burst time ");
        int bt = sc.nextInt();
        System.out.println("Enter priority(lesser the priority higher the no)");
        int priority = sc.nextInt();
        p[i] = new process(i + 1, at, bt);
        p1[i] = new process(i + 1, at, bt, priority);
        p2[i] = new process(i + 1, at, bt);
        p3[i] = new process(i + 1, at, bt);
      }
      fcfs f = new fcfs(p);
      f.execute();
      priority pobj = new priority(p1);
      pobj.execute();
      sjf s = new sjf(p2);
      s.execute();
      System.out.println("Enter time quantum: ");
      int tq = sc.nextInt();
      Round_robin r = new Round_robin(p3, tq);
      r.execute();
    }
  }
}
