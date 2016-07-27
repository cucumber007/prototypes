package com.cucumber007.prototypes.activities._architecture.mvp;

public class TestPresenter {

    private TestView testView;

    public TestPresenter(TestView testView) {
        this.testView = testView;
    }

    public  void notifyClick() {
        TestModel.modifyText(() -> testView.displayData(TestModel.getText()+" Presenter mod"));
    }

    public interface TestView {
        void displayData(String data);
    }

}
