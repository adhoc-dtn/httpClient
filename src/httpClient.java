import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class httpClient {
	public static void main(String[] args) {
		executeGet();
		// executePost();
	}


	// HTTPのGETリクエスト送信後、得られた文字列を標準出力
	private static void executeGet() {
		// System.out.println("===== HTTP GET Start =====");
		try {
			//サーバURL
			URL url = new URL("http://neteng.eng.niigata-u.ac.jp/");

			HttpURLConnection connection = null;

			// サーバとの接続後、HTTP GETリクエスト
			try {
				connection = (HttpURLConnection) url.openConnection(); // サーバとのコネクション
				connection.setRequestMethod("GET"); // GET

				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) { // GETの返答に対してHTTP
																					// OKが帰ってきたらということか
					try (InputStreamReader isr = new InputStreamReader(
							connection.getInputStream(), StandardCharsets.UTF_8);
							BufferedReader reader = new BufferedReader(isr)) {
						String line;
						// GETによって得られた文字列を標準出力
						while ((line = reader.readLine()) != null) {
							System.out.println(line);
						}
					}
				}
			} finally {
				// コネクションクローズ
				if (connection != null) {
					connection.disconnect();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// System.out.println("===== HTTP GET End =====");
	}

	//
	private static void executePost() {
		System.out.println("===== HTTP POST Start =====");
		try {
			URL url = new URL("http://localhost/post");

			HttpURLConnection connection = null;

			try {
				connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");

				BufferedWriter writer = new BufferedWriter(
						new OutputStreamWriter(connection.getOutputStream(),
								StandardCharsets.UTF_8));
				writer.write("POST Body");
				writer.write("\r\n");
				writer.write("Hello Http Server!!");
				writer.write("\r\n");
				writer.flush();

				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					try (InputStreamReader isr = new InputStreamReader(
							connection.getInputStream(), StandardCharsets.UTF_8);
							BufferedReader reader = new BufferedReader(isr)) {
						String line;
						while ((line = reader.readLine()) != null) {
							System.out.println(line);
						}
					}
				}
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("===== HTTP POST End =====");
	}
}
