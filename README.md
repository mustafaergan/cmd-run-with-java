# Java'dan Windows Komutu Çalıştırma

Spring Boot uygulaması içinden `ProcessBuilder` ile Windows komut satırı (`cmd.exe`) komutlarının nasıl
çalıştırılacağını ve çıktısının nasıl okunacağını gösteren deneme projesi. Örnek komut olarak yerel
`Administrators` grubuna kullanıcı ekleme/çıkarma (`net localgroup`) kullanılıyor.

> **Not:** Bu depo 2018 yılında deneme amacıyla yazıldı ve artık güncellenmiyor. Bağımlılıkları eskidir ve
> bilinen güvenlik açıkları içerir.

> **Uyarı:** Uygulama, kimlik doğrulaması olmayan HTTP uçlarından gelen bir kullanıcı adını doğrudan
> `cmd.exe` komutuna ekliyor. Bu, komut enjeksiyonuna ve yetki yükseltmeye açıktır. Yalnızca kapalı bir
> test makinesinde, ne yaptığını bilerek çalıştırın; hiçbir ağa açmayın.

## İçerik

| Dosya | Ne yapar |
|---|---|
| `AdminController.java` | `GET /admin/add/{name}` ve `GET /admin/remove/{name}`: kullanıcıyı yerel yöneticiler grubuna ekler / gruptan çıkarır, komut çıktısını döndürür |
| `ScheduledTasks.java` | `@Scheduled(fixedRate = 90000000)` ile açılışta ve yaklaşık 25 saatte bir aynı türde bir `net localgroup ... /delete` komutu çalıştırır (kullanıcı adı yer tutucudur) |
| `BatApplication.java`, `ServletInitializer.java` | Spring Boot giriş sınıfı; WAR olarak harici Tomcat'e de kurulabilir |

## Teknolojiler

- Java 8, Spring Boot 2.0.4 (Web, Quartz)
- Maven, `war` paketleme
- Yalnızca Windows'ta çalışır (`cmd.exe`, `net localgroup`); komutlar yönetici yetkisi ister

## Çalıştırma

```bash
mvn spring-boot:run
```

Uygulama `9292` portunda açılır. Komutların etkili olması için uygulamanın yönetici olarak çalıştırılması gerekir.
