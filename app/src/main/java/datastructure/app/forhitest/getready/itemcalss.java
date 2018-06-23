package datastructure.app.forhitest.getready;

public class itemcalss {
    private String mQuesstion;
    private String mAnswer;


    public itemcalss(String questionforiam, String answerforiam) {
        mQuesstion = questionforiam;
        mAnswer = answerforiam;
    }

    public String getQuestion() {
        return mQuesstion;
    }

    public String getAnswer() {
        return mAnswer;
    }

}
