package io.github.andrewjpro.gradletest;


import com.google.api.client.util.Lists;
import com.google.api.client.util.StringUtils;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;
import io.github.andrewjpro.gradletest.steam.SteamWishlistEmail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.github.andrewjpro.gradletest.gmail.GmailConnection.createGmailConnection;

public class ExecutionPgm {

    private static final DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;

    public static void main(String[] args) throws IOException {
        Gmail gmail = createGmailConnection();

        ListMessagesResponse messagesResponse = gmail.users().messages().list("me").setQ("Subject:An item on your Steam wishlist is on sale!").execute();

        List<Message> messageList =  messagesResponse.getMessages();

        for(Message messageID : messageList) {
            Message messageMetadata = gmail.users().messages().get("me", messageID.getId()).setFormat("full").execute();
            List<MessagePartHeader> headers = messageMetadata.getPayload().getHeaders();

            String date = "";
            for(MessagePartHeader header : headers) {
                if(header.getName().equalsIgnoreCase("date")) {
                    date = header.getValue();
                    break;
                }
            }

            String body = StringUtils.newStringUtf8(messageMetadata.getPayload().getParts().get(0).getBody().decodeData());
            BufferedReader rdr = new BufferedReader(new StringReader(body));
            List<String> lines = Lists.newArrayList();
            for (String line = rdr.readLine(); line != null; line = rdr.readLine()) {
                lines.add(line);
            }
            rdr.close();

            String gameName = lines.get(4);
            String gameUrl = lines.get(5);

            LocalDate parsedDate = LocalDate.parse(date, formatter);

            SteamWishlistEmail steamWishlistEmail = new SteamWishlistEmail(parsedDate, gameName, gameUrl);

            System.out.println(steamWishlistEmail);
        }
    }
}
