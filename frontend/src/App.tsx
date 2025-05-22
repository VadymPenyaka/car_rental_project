import { CarMainCategoriesPanel } from "./components/MainPageBlocks/CarMainCategoriesPanel/CarMainCategoriesPanel"
import CarRentalSearch from "./components/CarRentalSearch/CarRentalSearch"
import { CirclePercSection } from "./components/MainPageBlocks/CirclePercSection"
import { FAQSection } from "./components/MainPageBlocks/FAQSection"
import { FeaturesCarousel } from "./components/MainPageBlocks/FeaturesCarousel"
import { Footer } from "./components/Footer"
import { Header } from "./components/header/Header"
import Hero from "./components/Hero/Hero"
import { InfoReasonsSection } from "./components/MainPageBlocks/InfoReasonsSection"
import { RentalInfo } from "./components/MainPageBlocks/RentalInfo"
import { TermAndAdvantagesSection } from "./components/MainPageBlocks/TermAndAdvantagesSection"
import lvivPanorama from "./assets/lvivPanorama.jpg"

const App = () => {
	return (
		<>
			<Header />
			<Hero bgImage={lvivPanorama}>
				<CarRentalSearch />
			</Hero>
			<CarMainCategoriesPanel />
			<FeaturesCarousel />
			<CirclePercSection />
			<RentalInfo />
			<InfoReasonsSection />
			<TermAndAdvantagesSection />
			<FAQSection />
			<Footer />
		</>
	)
}

export default App