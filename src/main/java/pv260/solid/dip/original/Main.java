
package pv260.solid.dip.original;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        RecommendedOutfitService outfitService = context.getBean(RecommendedOutfitService.class);
        RecommendedLunchService lunchService =  context.getBean(RecommendedLunchService.class);

        System.out.println("o--         Awesome Lifestyle Page               --o");
        System.out.println("Tomorrow, it would be wise to dress like this:");
        System.out.println(outfitService.recommendOutfitForTomorrow());
        System.out.println("For lunch, we recommend that you:");
        System.out.println(lunchService.recommendLunchForTomorrow());
        System.out.println("o--                                              --o");
    }


}
