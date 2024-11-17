package org.example;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            String url = "http://www.cgv.co.kr/movies/";
            Document movieDocument = Jsoup.connect(url).get();

            Elements movielinks = movieDocument.select("div.box-image > a");
            for (Element link : movielinks) {
                // 영화 링크 따기
                String href = link.attr("href");

                // 영화 인물 링크 따기
                String charactorUrl = "http://www.cgv.co.kr/movies/detail-view/cast.aspx" + href.substring(20) + "#menu";
                Document charactorDocument = Jsoup.connect(charactorUrl).get();

                // 출연진 정보 가져오기 (배우)
                Elements actorLinks = charactorDocument.select("div.sect-staff-actor > ul > li > div.box-image > a");
                for (Element actorLink : actorLinks) {
                    String actorUrl = "http://www.cgv.co.kr" + actorLink.attr("href");
                    fetchPersonInfo(actorUrl, "배우");
                }

                // 감독 정보 가져오기
                Elements directorLinks = charactorDocument.select("div.sect-staff-director > ul > li > div.box-image > a");
                for (Element directorLink : directorLinks) {
                    String directorUrl = "http://www.cgv.co.kr" + directorLink.attr("href");
                    fetchPersonInfo(directorUrl, "감독");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 인물 정보를 가져오는 함수
    private static void fetchPersonInfo(String personUrl, String role) {
        try {
            Document personDocument = Jsoup.connect(personUrl).get();

            // 이름 가져오기
            Elements nameElements = personDocument.select("div.title > strong");
            for (Element nameElement : nameElements) {
                String name = nameElement.text();
                System.out.println(role + " 이름: " + name);
            }

            // 출생 정보 가져오기
            Elements birthElements = personDocument.select("div.spec dt:contains(출생) + dd");
            for (Element birthElement : birthElements) {
                String birth = birthElement.text();
                System.out.println(role + " 출생: " + birth);
            }

            // 국적 정보 가져오기
            Elements nationalityElements = personDocument.select("div.spec dt:contains(국적) + dd");
            for (Element nationalityElement : nationalityElements) {
                String nationality = nationalityElement.text();
                System.out.println(role + " 국적: " + nationality);
            }

            // 이미지 링크 가져오기
            Elements imageElements = personDocument.select("div.sect-base-people > div.sect-base > div.box-image > a");
            for (Element imageElement : imageElements) {
                String imageLink = imageElement.attr("href");
                System.out.println(role + " 이미지 링크: " + imageLink);
            }

            System.out.println("--------------------------------------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }

            //영화 링크 따기 (더보기 탭)
//            String apiUrl = "http://www.cgv.co.kr/common/ajax/movies.aspx/GetMovieMoreList?listType=1&orderType=1&filterType=1";
//            Connection.Response response = Jsoup.connect(apiUrl)
//                    .header("Content-Type", "application/json; charset=utf-8")
//                    .header("Accept", "application/json, text/javascript, */*; q=0.01")
//                    .ignoreContentType(true)
//                    .execute();
//
//            JSONObject jsonObject = new JSONObject(response.body());
//            String encodedData = jsonObject.getString("d");
//
//            JSONObject dataObject = new JSONObject(encodedData);
//            JSONArray movieList = dataObject.getJSONArray("List");
//
//            for (int i = 0; i < movieList.length(); i++) {
//                JSONObject movie = movieList.getJSONObject(i);
//                int movieIdx = movie.getInt("MovieIdx");
//
//                String detailUrl = "http://www.cgv.co.kr/movies/detail-view/?midx=" + movieIdx;
//                System.out.println(detailUrl);
//            }

//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }
    }