package pl.put.poznan.builder.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.builder.logic.*;
import pl.put.poznan.builder.logic.Object;

@RestController
@RequestMapping("/bootstrap")
public class BootstrapBuilderController {

    public static String main(String header, Boolean footer,String title,String description,String keywords,String tw_linkObrazek,String card,String og_linkObrazek,String linkUrl){
        String codeText= "";
       codeText+= new BootstrapBuilder(new Start()).run(new Start());

        if((title!="") && (description!="") && (keywords!="")){
           codeText+= new BootstrapBuilder(new Metatags(title, description, keywords)).run(new Metatags(title, description, keywords));
        }
        if((title!="") && (description!="") && (card!="") && (tw_linkObrazek!="")) {
           codeText+= new BootstrapBuilder(new MetaTwitter(title, description, tw_linkObrazek, card)).run(new MetaTwitter(title, description, tw_linkObrazek, card));
        }
        if((title!="") && (description!="") && (linkUrl!="") && (og_linkObrazek!="")) {
           codeText+= new BootstrapBuilder(new MetaOg(title, description, og_linkObrazek, linkUrl)).run(new MetaOg(title, description, og_linkObrazek, linkUrl));
        }
       codeText+= new BootstrapBuilder(new Middle()).run(new Middle());
        if(header=="static"){
           codeText+= new BootstrapBuilder(new Header()).run(new Header());
        }
        if(header=="fixed"){
           codeText+= new BootstrapBuilder(new Header()).run(new Header());
           codeText+= new BootstrapBuilder(new FixedHeader()).run(new FixedHeader());
        }
       codeText+= new BootstrapBuilder(new Object()).run(new Object());
        if(footer) {
           codeText+= new BootstrapBuilder(new Footer()).run(new Footer());
        }
       codeText+= new BootstrapBuilder(new End()).run(new End());

        return codeText;
    }
    private static final Logger logger = LoggerFactory.getLogger(BootstrapBuilderController.class);

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public String get(
            @RequestParam(value="header", defaultValue="") String header, //fixed or static
            @RequestParam(value="footer", defaultValue="false") Boolean footer,

            @RequestParam(value="title", defaultValue="") String title,

            @RequestParam(value="description", defaultValue="") String description,

            @RequestParam(value="keywords", defaultValue="") String keywords,
            @RequestParam(value="tw_linkObrazek", defaultValue="") String tw_linkObrazek,
            @RequestParam(value="card", defaultValue="") String card,
            @RequestParam(value="og_linkObrazek", defaultValue="") String og_linkObrazek,
            @RequestParam(value="linkUrl", defaultValue="") String linkUrl
            /*

            og rozdzielczosc
             */
    ){

        // log the parameters

        logger.debug(title);
        logger.debug(description);

        return main(header,footer,title,description,keywords,tw_linkObrazek,card,og_linkObrazek,linkUrl);
    }


}


