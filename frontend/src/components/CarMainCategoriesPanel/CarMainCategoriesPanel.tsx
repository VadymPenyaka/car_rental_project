import { ScrollArea, ScrollBar } from "@/components/ui/scroll-area"

export interface carCategory {
	categoryName: string
	imgURL: string
}

export const categories: carCategory[] = [
	{
		categoryName: "ECONOMY",
		imgURL: "https://7cars.com.ua/wp-content/uploads/2016/03/econom.jpg",
	},
	{
		categoryName: "MIDDLE",
		imgURL: "https://7cars.com.ua/wp-content/uploads/2016/03/middle.jpg",
	},
	{
		categoryName: "BUSINESS",
		imgURL: "https://7cars.com.ua/wp-content/uploads/2016/03/business.jpg",
	},
	{
		categoryName: "PREMIUM",
		imgURL: "https://7cars.com.ua/wp-content/uploads/2016/03/premium.jpg",
	},
	{
		categoryName: "SUV",
		imgURL: "https://7cars.com.ua/wp-content/uploads/2016/03/offroad.jpg",
	},
	{
		categoryName: "MINIVAN",
		imgURL: "https://7cars.com.ua/wp-content/uploads/2016/03/minivan.jpg",
	},

]

export function CarMainCategoriesPanel() {
	return (
		<ScrollArea className="flex max-w-5xl mx-auto whitespace-nowrap rounded-md border">
			<div className="max-w-5xl mx-auto flex justify-between p-4">
				{categories.map((carCategory) => (
					<figure key={carCategory.categoryName} className="shrink-0">
						<div className="overflow-hidden rounded-md w-44">
							<img
								src={carCategory.imgURL}
								alt={`Category - ${carCategory.categoryName}`}
								className="aspect-[4/3] h-fit w-fit object-scale-down"
							/>
						</div>
						<figcaption className="pt-2 text-xs text-muted-foreground">
							{" "}
							<span className="font-semibold text-foreground">
								{carCategory.categoryName}
							</span>
						</figcaption>
					</figure>
				))}
			</div>
			<ScrollBar orientation="horizontal" />
		</ScrollArea>
	)
}