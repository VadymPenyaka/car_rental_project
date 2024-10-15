import lvivPanorama from "../../assets/lvivPanorama.jpg"

export default function Hero() {
  return (
    <div className="bg-cover bg-center h-[535px]"
    style={{
      backgroundImage: `url(${lvivPanorama})`,
    }}>
        <h1>Автопрокат, якому довіряють!</h1>
        <p><span>4 / 5 ★ ★ ★ ★ ☆</span> за <a href="#">Google відгуками</a></p>
        
    </div>
  )
}