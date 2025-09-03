import android.app.Application

class App:Application(){
    override  fun onCreate(){
        super.oncreate()
        startKoin{
            androidContext(this@App)
            modules{}
        }
    }
}