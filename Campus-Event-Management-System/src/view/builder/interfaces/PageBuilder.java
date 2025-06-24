package view.builder.interfaces;

import model.PageModel;

public interface PageBuilder {
    void setTitle(String title);

    void buildHeader();

    void buildContent();

    void buildFooter();

    PageModel getPageModel();
}
