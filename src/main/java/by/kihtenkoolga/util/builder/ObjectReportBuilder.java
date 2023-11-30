package by.kihtenkoolga.util.builder;

import java.util.Date;
import java.util.List;

public abstract class ObjectReportBuilder<B, T> {

    private String head = "";
    private String body = "";

    /**
     * Устанавливает верхушку отчета с текущей датой
     * @param headText заголовок отчета
     * @return строка - заголовок отчета
     */
    public B withHead(String headText) {
        head = String.format("""
                CREATION DATE: %s
                %s
                """, new Date(), headText);;
        return (B) this;

    }

    /**
     * Устанавливает содержимое отчета, в соответствии с значением каждого объекта
     * используя метод {@link #toStringPerformance(T object)}
     * @param objects список объектов отчета
     * @return строка - тело отчета
     */
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

    /**
     * Устанавливает содержимое отчета, в соответствии с значением объекта
     * используя метод {@link #toStringPerformance(T object)}
     * @param object объект отчета
     * @return строка - тело отчета
     */
    public B withBody(T object) {
        body = toStringPerformance(object);
        return (B) this;
    }

    /**
     * @return построенный отчет
     */
    public String build() {
        return head + body;
    }

    public abstract String toStringPerformance(T object);

}
