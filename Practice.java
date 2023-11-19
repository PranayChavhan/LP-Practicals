import java.util.*;



class sjf{
    process processes[];
    double avgtat = 0;
    double avgwt = 0;

    public sjf(process processes[]){
        this.processes = processes;
    }

    void execute(){
        int n = processes.length;
        boolean executed[] = new boolean[n];

        int completed = 0, time = 0;

        while(completed != n){
            int index = -1;
            int min = 500;

            for(int i = 0; i < n; i++){
                
            }
    }
    }
}

public class Practice {

    public static void FIFO(int numFrames, List<Integer> pages){
        Queue<Integer> frameQueue = new LinkedList<>();
        Set<Integer> frameSet = new HashSet<>();

        int pageFaults = 0;

        for(int page : pages){
            if (!frameSet.contains(page)) {
                if (frameQueue.size() == numFrames) {
                    int removedPage = frameQueue.poll();
                    frameSet.remove(removedPage);
                }
                frameQueue.offer(page);
                frameSet.add(page);

                pageFaults++;
                System.out.println("Page Reference: " + page + " | Statue: Miss");
            } else{
                System.out.println("Page Reference: " + page + " | Statue: Hit");
            }
        }

        System.out.println("Total Page Fault: " + pageFaults);
    }








    

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of frames: ");
        int numFrames = sc.nextInt();

        System.out.println("Enter the number of pages: ");
        int numPages = sc.nextInt();

        System.out.println("Enter the page reference string: ");
        List<Integer> pages = new ArrayList<>();

        for(int i = 0; i < numPages; i++){
            pages.add(sc.nextInt());
        }
    }
}
