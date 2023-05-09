package com.sber.sber.sheduled;

import com.sber.sber.entity.Movie;
import com.sber.sber.entity.News;
import com.sber.sber.entity.Notification;
import com.sber.sber.entity.Ticket;
import com.sber.sber.repository.NewsRepository;
import com.sber.sber.repository.NotificationRepository;
import com.sber.sber.repository.TicketRepository;
import com.sber.sber.service.NotificationService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class SheduledTask {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;

    @Scheduled(cron = "@hourly") //удаляет все тикеты, где поле complete = true
    public void clearCompletedTicket() {
        List<Ticket> tickets = ticketRepository.findAll().stream()
                .filter(Ticket::isComplete)
                .collect(Collectors.toList());

        ticketRepository.deleteAll(tickets);
    }

    @Scheduled(cron = "* 1 0 * * *") //удаляет все тикеты , где дата исполнения меньше текущей даты
    public void clearOverdueTicket() {
        LocalDate nowLocalDate = LocalDate.now();
        List<Ticket> overdueTickets = ticketRepository.findByExecutionDateLessThan(nowLocalDate);
        ticketRepository.deleteAll(overdueTickets);
    }

    @Scheduled(cron = "* */30 * * * *") //считывает новые новости с "https://www.film.ru/news"
    public void findNewsInInternet() {
        String url = "https://www.film.ru/news";
        String shortUrl = "https://www.film.ru/";
        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozzila")
                    .referrer("https://google.com")
                    .get();
            Elements elementsTopic = document.getElementsByClass("topic");
            List<Node> nodesChild = Objects.requireNonNull(elementsTopic.first()).childNodes();

            List<String> allNews = newsRepository.findLastNews();
            for (Node node : nodesChild) {
                if (node instanceof Element) {
                    String title = node.attr("title") + ". Читайте на " + shortUrl + node.attr("href");
                    if (!allNews.contains(title)) {
                        News news = new News();
                        news.setTitle(title);
                        newsRepository.save(news);
                        notificationService.addNewNewsMassNotification(title);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Scheduled(cron = "* 1 0 * * *") // раз в сутки удаляет все Notification из базы данных
    public void clearOverdueNotification() {
        List<Notification> overdueNotification = notificationRepository.findAll();
        notificationRepository.deleteAll(overdueNotification);
    }

    @Scheduled(cron = "* 1 0 * * *")// раз в сутки удаляет все News из базы данных
    public void clearOverdueNews() {
        List<News> overdueNews = newsRepository.findAll();
        newsRepository.deleteAll(overdueNews);
    }
}
