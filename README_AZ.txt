GITHUB MOBILE UPLOADER — APK layihəsi

1) Bu Android layihəsini GitHub repozitoriyasına yerləşdir.
2) GitHub -> Actions -> Build GitHub Mobile Uploader APK -> Run workflow.
3) Build bitdikdən sonra GitHub Actions artifact bölməsindən GitHub-Mobile-Uploader-APK faylını endir və APK-nı quraşdır.
4) Termux-da ~/glgonder/app.py mövcud olmalıdır. start.sh/stop.sh/restart.sh fayllarını ~/glgonder qovluğuna köçür və icra icazəsi ver:
   chmod +x ~/glgonder/{start,stop,restart}.sh
5) Termux-da tmux, python və layihənin app.py üçün lazım olan paketləri quraşdırılmış olmalıdır.
6) Termux-da ~/.termux/termux.properties faylında allow-external-apps=true olmalıdır. Sonra Termux-u bağla/aç.
7) Android Settings -> Apps -> GitHub Mobile Uploader -> Permissions -> Additional permissions -> Run commands in Termux environment icazəsini ver.
8) APK-nı aç və START bas.

QEYD:
- APK Chrome açmır; panel öz daxilində WebView ilə göstərilir.
- Termux RUN_COMMAND rəsmi interfeysindən istifadə olunur. Google Play Termux quruluşunda bu interfeysin mövcudluğu versiyadan asılı ola bilər. Əmr işləməsə Termux-u yenilə və versiyanı yoxla.
- Android batareya idarəetməsi Termux-u dayandıra bilər. Battery optimization istisnası verilməsi faydalıdır.
- Force Stop, cihazın yenidən başlaması və Android-in fon məhdudiyyətləri 24/7 işləməyə zəmanət vermir.
- RUN_COMMAND xarici tətbiqə Termux-da əmr icra etmək səlahiyyəti verir. Bu icazəni yalnız öz hazırladığın APK-ya ver.
