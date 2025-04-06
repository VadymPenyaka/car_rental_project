import * as React from "react"

import { cn } from "@/lib/utils"
import {
	NavigationMenu,
	NavigationMenuContent,
	NavigationMenuItem,
	NavigationMenuLink,
	NavigationMenuList,
	NavigationMenuTrigger,
} from "@/components/ui/navigation-menu"

const CarParkComponents: { title: string; href: string; description: string }[] = [
	{
		title: "Car Rental in Kyiv",
		href: "/services/rentals/kyiv",
		description:
			"Car rental in Kyiv. A wide selection of vehicles at affordable prices.",
	},
	{
		title: "SUV Rental",
		href: "/services/rentals/suvs",
		description:
			"SUV rental for comfortable trips around the city and beyond.",
	},
	{
		title: "Electric Car Rental",
		href: "/services/rentals/electric",
		description:
			"A green alternative - rent an electric car for eco-friendly travel.",
	},
	{
		title: "Car Rental in Lviv",
		href: "/services/rentals/lviv",
		description:
			"Car rental in Lviv. Convenient conditions and a wide selection of cars for every taste.",
	},
	{
		title: "Minivan Rental",
		href: "/services/rentals/minivans",
		description:
			"Spacious minivan rental for trips with a large group or family.",
	},
	{
		title: "Luxury Car Rental",
		href: "/services/rentals/luxury",
		description:
			"Premium-class car rental for those who appreciate comfort and style.",
	},
];

const AdditionalServiceComponents: { title: string; href: string; description: string }[] = [
	{
		title: "Long-term Car Rental",
		href: "/services/long-term-rental",
		description:
			"Favorable conditions for long-term car rental for a period of one month or more.",
	},
	{
		title: "Car Rental with Driver",
		href: "/services/rentals/with-driver",
		description:
			"Car rental with a driver for special events, business meetings, or tourist trips.",
	},
];

const onlyTitleButtonsText: string[] = [
	"Car Leasing",
	"For Business",
	"Rental Terms",
	"Contacts",
	"About Us",
	"Reviews",
	"Blog"
];

export const HeaderNavigation = () => {
	return (
		<NavigationMenu className="w-full mt-10">
			<NavigationMenuList className="w-full">
				<NavigationMenuItem>
					<ListItem title="Home" key={"home"}></ListItem>
				</NavigationMenuItem>
				<NavigationMenuItem>
					<NavigationMenuTrigger className="text-l">Car Park</NavigationMenuTrigger>
					<NavigationMenuContent>
						<ul className="grid w-[400px] gap-3 md:w-[500px] md:grid-cols-1 lg:w-[600px]">
							{CarParkComponents.map((component) => (
								<ListItem
									key={component.title}
									title={component.title}
									href={component.href}
								>
									{component.description}
								</ListItem>
							))}
						</ul>
					</NavigationMenuContent>
				</NavigationMenuItem>
				<NavigationMenuItem>
					<NavigationMenuTrigger className="text-l">Other Services</NavigationMenuTrigger>
					<NavigationMenuContent>
						<ul className="grid w-[400px] gap-3 md:w-[500px] md:grid-cols-1 lg:w-[600px]">
							{AdditionalServiceComponents.map((component) => (
								<ListItem
									key={component.title}
									title={component.title}
									href={component.href}
								>
									{component.description}
								</ListItem>
							))}
						</ul>
					</NavigationMenuContent>
				</NavigationMenuItem>
				{onlyTitleButtonsText.map((title: string) =>
					<NavigationMenuItem key={title}>
						<ListItem title={title} key={title}></ListItem>
					</NavigationMenuItem>)
				}
			</NavigationMenuList>
		</NavigationMenu>
	)
}

const ListItem = React.forwardRef<
	React.ElementRef<"a">,
	React.ComponentPropsWithoutRef<"a">
>(({ className, title, children, ...props }, ref) => {
	return (
		<li>
			<NavigationMenuLink asChild>
				<a
					ref={ref}
					className={cn(
						"block select-none space-y-1 rounded-md p-3 leading-none no-underline outline-none transition-colors hover:bg-accent hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground",
						className
					)}
					{...props}
				>
					<div className="text-l text-nowrap font-medium leading-none">{title}</div>
					<p className="line-clamp-2 text-sm leading-snug text-muted-foreground">
						{children}
					</p>
				</a>
			</NavigationMenuLink>
		</li>
	)
})
ListItem.displayName = "ListItem"