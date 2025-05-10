import { CarMainCategoriesPanel } from "./components/CarMainCategoriesPanel/CarMainCategoriesPanel"
import { CirclePercSection } from "./components/CirclePercSection"
import { FAQSection } from "./components/FAQSection"
import { FeaturesCarousel } from "./components/FeaturesCarousel"
import { Footer } from "./components/Footer"
import { Header } from "./components/header/Header"
import Hero from "./components/Hero/Hero"
import { InfoReasonsSection } from "./components/InfoReasonsSection"
import { RentalInfo } from "./components/RentalInfo"
import { TermAndAdvantagesSection } from "./components/TermAndAdvantagesSection"

const App = () => {
	return (
		<>
			<Header />
			<Hero />
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