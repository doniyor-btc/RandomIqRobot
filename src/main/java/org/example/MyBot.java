package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MyBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "wikimchabot";
    }

    @Override
    public String getBotToken() {
        return "5843189689:AAFj0PcvKQ5UeZjFCdYLpVtaFNegxfzXUqM";
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {

            if (update.hasMessage() &&
                    update.getMessage().hasText()) {

                User user = update.getMessage().getFrom();
                Long chatId = update.getMessage().getChatId();

                startBotMessage(user, chatId, update);

                String text = update.getMessage().getText();
                Runnable runnable = () -> {
                    try {

                        Thread.sleep(3500);
                        sendDefaultMessage(chatId);

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                };
                if (text.equals("/start")) {
                    Thread thread = new Thread(runnable);
                    thread.start();
                }
                chooseProgrammingLanguage(chatId, update, text);
                sendPdfDocument(chatId, update, text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPdfDocument(Long chatId, Update update, String text) {

        File javaScriptpdfFile = new File("docs/JavaScriptInterviewBitQuestion_compressed.pdf");
        File javapdfFile = new File("docs/javaInterview_compressed.pdf");
        File cpdfFile = new File("docs/C++InterviewQuestion_compressed.pdf");
        File pythonpdfFile = new File("docs/PythonInterviewQuestion_compressed.pdf");

        switch (text) {
            case "Javascript dan savollarga tayyorlanish" -> {
                SendDocument sendDocument = new SendDocument();
                sendDocument.setChatId(chatId);
                sendDocument.setDocument(new InputFile(javaScriptpdfFile));
                try {
                    execute(sendDocument);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            case "Java dan savollarga tayyorlanish" -> {
                SendDocument sendDocument = new SendDocument();
                sendDocument.setChatId(chatId);
                sendDocument.setDocument(new InputFile(javapdfFile));
                try {
                    execute(sendDocument);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            case "C++ dan savollarga tayyorlanish" -> {
                SendDocument sendDocument = new SendDocument();
                sendDocument.setChatId(chatId);
                sendDocument.setDocument(new InputFile(cpdfFile));
                try {
                    execute(sendDocument);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            case "Python dan savollarga tayyorlanish" -> {
                SendDocument sendDocument = new SendDocument();
                sendDocument.setChatId(chatId);
                sendDocument.setDocument(new InputFile(pythonpdfFile));
                try {
                    execute(sendDocument);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            default -> {
                if (text.contains(" dan savollarni ishlash")) {
                    startQuestionTest(update, chatId);
                }
            }
        }

    }

    private void startQuestionTest(Update update, Long chatId) {
        try {
            String text = update.getMessage().getText();

            switch (text) {
                case "Java dan savollarni ishlash" -> {
                    String file = "interviewquestion.txt";

                    String questions = getQuestions(file);
                    SendMessage message = new SendMessage();

                    message.setChatId(chatId);
                    message.setText(questions);

                    try {
                        execute(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case "C++ dan savollarni ishlash" -> {
                    String file = "c++.txt";

                    String questions = getQuestions(file);
                    SendMessage message = new SendMessage();

                    message.setChatId(chatId);
                    message.setText(questions);

                    try {
                        execute(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case "Python dan savollarni ishlash" -> {

                    String file = "python.txt";

                    String questions = getQuestions(file);
                    SendMessage message = new SendMessage();

                    message.setChatId(chatId);
                    message.setText(questions);

                    try {
                        execute(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case "Javascript dan savollarni ishlash" -> {

                    String file = "javascript.txt";

                    String questions = getQuestions(file);
                    SendMessage message = new SendMessage();

                    message.setChatId(chatId);
                    message.setText(questions);

                    try {
                        execute(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getQuestions(String file) {
        try {
            List<String> questionList = extractQuestion(file);
            StringBuilder sb = new StringBuilder();

            Collections.shuffle(questionList);
            Random random = new Random();

            for (int i = 1; i < 11; i++) {
                String s = questionList.get(random.nextInt(questionList.size()));
                sb.append(i).append(". ").append(s).append("\n");
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Savollar topilmay qoldi";
    }

    private List<String> extractQuestion(String file) {
        try {

            List<String> questions = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    questions.add(line.trim());
                }
                reader.close();
                return questions;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void startBotMessage(User user, Long chatId, Update update) {
        try {
            String text = update.getMessage().getText();
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Assalamo alaykum. " + user.getFirstName() + "\n" + "Bu Telegram botda dasturlashdan interview \n" + "question savolariga tayyorlanishingiz mumkin \n");

            try {
                if (text.equals("/start"))
                    execute(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendDefaultMessage(Long chatId) {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Biror dasturlash tilini tanlang: ");
            message.setReplyMarkup(getLanguageSelectionKeyboard());
            try {
                execute(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chooseProgrammingLanguage(Long chatId, Update update, String text) {
        try {
            Message getLanguage = update.getMessage();
            if (update.hasMessage() && getLanguage.hasText()) {
                switch (text) {
                    case "Java" -> startJavaLan(text, chatId);
                    case "Python" -> startPython(text, chatId);
                    case "C++" -> startC(text, chatId);
                    case "Javascript" -> startJavascript(text, chatId);
                    default -> {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("Biror dasturlash tilini tanlang: ");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void startJavascript(String languageProgramming, Long chatId) {
        getLanguage(languageProgramming, chatId);
    }

    private void getLanguage(String languageProgramming, Long chatId) {
        SendMessage message = new SendMessage();
        ReplyKeyboardMarkup keyboardMarkup = getOptionalCommand(languageProgramming);
        message.setChatId(chatId);
        message.setText("Tanlang: ");
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startC(String languageProgramming, Long chatId) {
        getLanguage(languageProgramming, chatId);
    }

    private void startPython(String languageProgramming, Long chatId) {
        getLanguage(languageProgramming, chatId);
    }

    private void startJavaLan(String languageProgramming, Long chatId) {
        getLanguage(languageProgramming, chatId);
    }

    private ReplyKeyboard getLanguageSelectionKeyboard() {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Java"));
        row1.add(new KeyboardButton("Python"));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("C++"));
        row2.add(new KeyboardButton("Javascript"));

        keyboardMarkup.setKeyboard(List.of(row1, row2));

        return keyboardMarkup;
    }

    private ReplyKeyboardMarkup getOptionalCommand(String languageProgramming) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);

        KeyboardRow row1 = new KeyboardRow();

        row1.add(new KeyboardButton(languageProgramming + " dan savollarni ishlash"));
        row1.add(new KeyboardButton(languageProgramming + " dan savollarga tayyorlanish"));

        keyboardMarkup.setKeyboard(List.of(row1));
        return keyboardMarkup;
    }
}
