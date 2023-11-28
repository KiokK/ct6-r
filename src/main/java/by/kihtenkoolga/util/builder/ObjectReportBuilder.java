package by.kihtenkoolga.util.builder;

import java.util.Date;
import java.util.List;

public abstract class ObjectReportBuilder<B, T> {

    private String head = "";
    private String body = "";

    public B withHead(String headText) {
        head = String.format("""
                CREATION DATE: %s
                %s
                """, new Date(), headText);;
        return (B) this;

    }

    public B withBody(List<T> objects) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < objects.size(); i++) {
            stringBuilder.append(i + 1)
                    .append(". ")
                    .append(toStringPerformance(objects.get(i)));
        }
        body = stringBuilder.toString();
        return (B) this;

    }

    public B withBody(T object) {
        body = toStringPerformance(object);
        return (B) this;
    }

    public String build() {
        return head + body;
    }

    public abstract String toStringPerformance(T object);

}
