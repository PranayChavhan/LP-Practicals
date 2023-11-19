import java.util.*;

public class FIFOPageReplacement {

    public static void FIFO(int numFrames, List<Integer> pages) {
        Queue<Integer> frameQueue = new LinkedList<>();
        Set<Integer> frameSet = new HashSet<>();

        int pageFaults = 0;

        for (int page : pages) {
            if (!frameSet.contains(page)) {
                if (frameQueue.size() == numFrames) {
                    int removedPage = frameQueue.poll();
                    frameSet.remove(removedPage);
                }
                frameQueue.offer(page);
                frameSet.add(page);

                pageFaults++;

                System.out.println("Page Reference: " + page + " | Status: Miss");
            } else {
                System.out.println("Page Reference: " + page + " | Status: Hit");
            }
        }
        System.out.println("Total Page Faults: " + pageFaults);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of frames: ");
        int numFrames = scanner.nextInt();

        System.out.print("Enter the number of pages: ");
        int numPages = scanner.nextInt();

        System.out.print("Enter the page reference string: ");
        List<Integer> pages = new ArrayList<>();
        for (int i = 0; i < numPages; ++i) {
            pages.add(scanner.nextInt());
        }

        System.out.println("\nFIFO Algorithm:");
        FIFO(numFrames, new ArrayList<>(pages));
        scanner.close();
    }
}
