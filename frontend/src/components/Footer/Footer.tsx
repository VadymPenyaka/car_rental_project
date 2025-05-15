import {
	Card,
	CardHeader,
	CardTitle,
	CardContent,
} from "@/components/ui/card"
import {
	NavigationMenu,
	NavigationMenuList,
	NavigationMenuItem,
	NavigationMenuLink,
} from "@/components/ui/navigation-menu"

import viber from '../../assets/viber.png'
import telegram from '../../assets/telegram.png'
import whatsup from '../../assets/whatsup.png'
import phone from '../../assets/smartphone.svg'

import Logo from "../ui/Logo"

export const Footer = () => (
	<footer className="bg-gray-800 text-gray-300">
		<div className="w-full max-w-5xl mx-auto px-4 py-12 mt-12 grid grid-cols-1 md:grid-cols-3 gap-8">

			{/* Column 1: Brand */}
			<Card className="bg-transparent shadow-none">
				<CardHeader>
					<CardTitle className="text-white">5Cars</CardTitle>
				</CardHeader>
				<CardContent>
					<p className="text-sm text-gray-300">
						© {new Date().getFullYear()} 5Cars, Inc.<br />
						All rights reserved.
					</p>
				</CardContent>
			</Card>

			{/* Column 2: Quick Links */}
			<Card className="bg-transparent shadow-none">
				<CardHeader>
					<CardTitle className="text-white">Quick Links</CardTitle>
				</CardHeader>
				<CardContent className="p-0">
					<NavigationMenu>
						<NavigationMenuList className="flex flex-col space-y-2">
							{["Home", "Fleet", "About Us", "Contact"].map((link) => (
								<NavigationMenuItem key={link}>
									<NavigationMenuLink
										href={`/${link.replace(/\s+/g, "-").toLowerCase()}`}
										className="text-sm hover:text-white text-gray-300 transition-colors duration-200"
									>
										{link}
									</NavigationMenuLink>
								</NavigationMenuItem>
							))}
						</NavigationMenuList>
					</NavigationMenu>
				</CardContent>
			</Card>

			{/* Column 3: Contact & Social */}
			<Card className="bg-transparent shadow-none text-gray-300">
				<CardHeader>
					<CardTitle className="text-white">Contact Us</CardTitle>
				</CardHeader>
				<CardContent>
					<p className="text-sm">
						Vokzalna Square 1,<br />
						Kyiv, 01001
					</p>
					<p className="text-sm mt-2">info@5cars.example</p>
					<div className="flex space-x-4 mt-4">
						<Logo
							src={phone}
							width='14rem'
						/>
						<p>+38 (067) 521 78 77</p>
						<Logo
							src={viber}
							width='20rem'
						/>
						<Logo
							src={telegram}
							width='20rem'
						/>
						<Logo
							src={whatsup}
							width='20rem'
						/>
					</div>
				</CardContent>
			</Card>
		</div>
	</footer>
)