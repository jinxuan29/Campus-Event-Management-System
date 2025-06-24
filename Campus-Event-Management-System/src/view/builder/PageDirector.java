package view.builder;

import model.PageModel;
import view.builder.interfaces.PageBuilder;

public class PageDirector {
    private final PageBuilder builder;

    public PageDirector(PageBuilder builder) {
        this.builder = builder;
    }

    // normal page
    public PageModel constructPage(String title) {
        builder.setTitle(title);
        builder.buildHeader();
        builder.buildContent();
        builder.buildFooter();
        return builder.getPageModel();
    }
}
