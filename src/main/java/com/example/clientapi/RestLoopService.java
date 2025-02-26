package com.example.clientapi;



import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RestLoopService {

    private final RestTemplate restTemplate;

    public RestLoopService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendRequestsInLoop(String url, int totalRequests, long delayMillis) {
        for (int i = 0; i < totalRequests; i++) {
            try {

                List<Transaction> transactionList = new ArrayList<>();

                for (int j = 0; j < totalRequests; j++) {
                    Transaction transaction = new Transaction();
                    transaction.setTransId((long) (i + 1000));
                    transaction.setUserId((long) (i + 1000));
                    transaction.setAmount(BigDecimal.valueOf((long) (i + 1000)));
                    transaction.setTimestamp( new Timestamp(i));
                    transaction.setCountry(TransactionCountry.getRandomCountry().toString());
                    transaction.setLatitudeCoord(20.2);
                    transaction.setLongtudeCoord(60.2);
                    transactionList.add(transaction);  // Add to list
                }

                // Set headers
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "application/json");

                // Create request entity (list of transactions)
                HttpEntity<List<Transaction>> requestEntity = new HttpEntity<>(transactionList, headers);

                // Send POST request
                String response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class).getBody();
                System.out.println("Response for fraud transactions: " + response);
                // Wait before sending the next request
                TimeUnit.MILLISECONDS.sleep(delayMillis);
            } catch (Exception e) {
                System.err.println("Request " + (i + 1) + " failed: " + e.getMessage());
            }
        }
    }

    public static class TransactionCountry {

        public enum Country {
            AFGHANISTAN, ALBANIA, ALGERIA, ANDORRA, ANGOLA, ANTIGUA_AND_BARBUDA, ARGENTINA, ARMENIA, AUSTRALIA, AUSTRIA,
            AZERBAIJAN, BAHAMAS, BAHRAIN, BANGLADESH, BARBADOS, BELARUS, BELGIUM, BELIZE, BENIN, BHUTAN, BOLIVIA,
            BOSNIA_AND_HERZEGOVINA, BOTSWANA, BRAZIL, BRUNEI, BULGARIA, BURKINA_FASO, BURUNDI, CABO_VERDE, CAMBODIA,
            CAMEROON, CANADA, CENTRAL_AFRICAN_REPUBLIC, CHAD, CHILE, CHINA, COLOMBIA, COMOROS, CONGO_BRAZZAVILLE, CONGO_KINSHASA,
            COSTA_RICA, CROATIA, CUBA, CYPRUS, CZECHIA, DENMARK, DJIBOUTI, DOMINICA, DOMINICAN_REPUBLIC, EAST_TIMOR, ECUADOR,
            EGYPT, EL_SALVADOR, EQUATORIAL_GUINEA, ERITREA, ESTONIA, ESWATINI, ETHIOPIA, FIJI, FINLAND, FRANCE, GABON,
            GAMBIA, GEORGIA, GERMANY, GHANA, GREECE, GRENADA, GUATEMALA, GUINEA, GUINEA_BISSAU, GUYANA, HAITI, HONDURAS,
            HUNGARY, ICELAND, INDIA, INDONESIA, IRAN, IRAQ, IRELAND, ISRAEL, ITALY, JAMAICA, JAPAN, JORDAN, KAZAKHSTAN,
            KENYA, KIRIBATI, KOREA_NORTH, KOREA_SOUTH, KOSOVO, KUWAIT, KYRGYZSTAN, LAOS, LATVIA, LEBANON, LESOTHO, LIBERIA,
            LIBYA, LIECHTENSTEIN, LITHUANIA, LUXEMBOURG, MADAGASCAR, MALAWI, MALAYSIA, MALDIVES, MALI, MALTA, MARSHALL_ISLANDS,
            MAURITANIA, MAURITIUS, MEXICO, MICRONESIA, MOLDOVA, MONACO, MONGOLIA, MONTENEGRO, MOROCCO, MOZAMBIQUE, MYANMAR,
            NAMIBIA, NAURU, NEPAL, NETHERLANDS, NEW_ZEALAND, NICARAGUA, NIGER, NIGERIA, NORTH_MACEDONIA, NORWAY, OMAN, PAKISTAN,
            PALAU, PALESTINE, PANAMA, PAPUA_NEW_GUINEA, PARAGUAY, PERU, PHILIPPINES, POLAND, PORTUGAL, QATAR, ROMANIA, RUSSIA,
            RWANDA, SAINT_KITTS_AND_NEVIS, SAINT_LUCIA, SAINT_VINCENT_AND_GRENADINES, SAMOA, SAN_MARINO, SAO_TOME_AND_PRINCIPE,
            SAUDI_ARABIA, SENEGAL, SERBIA, SEYCHELLES, SIERRA_LEONE, SINGAPORE, SLOVAKIA, SLOVENIA, SOLOMON_ISLANDS, SOMALIA,
            SOUTH_AFRICA, SOUTH_SUDAN, SPAIN, SRI_LANKA, SUDAN, SURINAME, SWEDEN, SWITZERLAND, SYRIA, TAIWAN, TAJIKISTAN,
            TANZANIA, THAILAND, TOGO, TONGA, TRINIDAD_AND_TOBAGO, TUNISIA, TURKEY, TURKMENISTAN, TUVALU, UGANDA, UKRAINE,
            UNITED_ARAB_EMIRATES, UNITED_KINGDOM, UNITED_STATES, URUGUAY, UZBEKISTAN, VANUATU, VATICAN_CITY, VENEZUELA,
            VIETNAM, YEMEN, ZAMBIA, ZIMBABWE
        }

        public static Country getRandomCountry() {
            Random random = new Random();
            Country[] countries = Country.values();
            return countries[random.nextInt(countries.length)];
        }
    }

}