import lvivPanorama from "../../assets/lvivPanorama.jpg"
import CarRentalSearch from "../CarRentalSearch/CarRentalSearch"

export default function Hero() {
	return (
		<div className="bg-cover bg-center h-[535px] flex items-center justify-center"
			style={{
				backgroundImage: `url(${lvivPanorama})`,
			}}>
			<CarRentalSearch />
		</div>
	)
}