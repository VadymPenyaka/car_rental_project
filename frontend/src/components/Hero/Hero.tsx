import { FC, memo, ReactNode } from "react"
import clsx from "clsx"

interface HeroProps {
	bgImage: string
	heightClass?: string
	className?: string
	children: ReactNode
}

const Hero: FC<HeroProps> = memo(({ bgImage, className, children }) => (
	<section
		role="banner"
		aria-label="Hero section"
		className={clsx(
			"w-full bg-cover bg-center flex items-center justify-center py-20",
			className
		)}
		style={{ backgroundImage: `url(${bgImage})` }}
	>
		{children}
	</section>
))

Hero.displayName = "Hero"
export default Hero