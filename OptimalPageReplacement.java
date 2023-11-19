import java.util.*;

public class OptimalPageReplacement {

    public static int optimalPageReplacement(int capacity, List<Integer> pages) {
        int pageFaults = 0;
        Map<Integer, Integer> pageIndices = new HashMap<>();
        List<Integer> frames = new ArrayList<>(Collections.nCopies(capacity, Integer.MAX_VALUE));

        for (int i = 0; i < pages.size(); ++i) {
            int page = pages.get(i);

            if (pageIndices.containsKey(page)) {
                frames.set(pageIndices.get(page), Integer.MAX_VALUE);
                System.out.println("Page Reference: " + page + " | Status: Hit");
            } else {
                int maxIndex = 0;
                int nextPage = frames.get(0);

                for (int j = 0; j < capacity; ++j) {
                    if (frames.get(j) == Integer.MAX_VALUE) {
                        maxIndex = j;
                        nextPage = Integer.MAX_VALUE;
                        break;
                    }

                    if (frames.get(j) > frames.get(maxIndex)) {
                        maxIndex = j;
                        nextPage = frames.get(j);
                    }
                }

                frames.set(maxIndex, page);
                pageIndices.put(page, i);
                pageFaults++;

                System.out.println("Page Reference: " + page + " | Status: Miss");
            }
        }

        return pageFaults;
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

        int optimalPageFaults = optimalPageReplacement(numFrames, new ArrayList<>(pages));
        System.out.println("Total Page Faults (Optimal): " + optimalPageFaults);
    }
}
