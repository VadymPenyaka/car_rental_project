import { AnimatedCircle } from "../ui/AnimatedCircle"

const stats = [
	{ percentage: 89, text: "of customers rated our cars as excellent" },
	{ percentage: 92, text: "of clients quickly received all the necessary information at the first contact with a manager" },
	{ percentage: 94, text: "of clients were given a car no more than 30 minutes after arrival at the office" },
	{ percentage: 96, text: "of customers noted that they received a clean car" },
	{ percentage: 100, text: "of customers received a car with a full tank" },
	{ percentage: 93, text: "of clients handed over their cars and returned their deposit in less than 20 minutes" },
]

export const CirclePercSection = () => {
	return (
		<div className="text-center">
			<h2 className="text-2xl md:text-4xl font-bold">What we are appriciated for</h2>
			<p className="text-gray-500">The data provided below is the summarized result of a survey that was conducted among 198 new customers</p>
			<div className="flex max-w-5xl mx-auto flex-wrap items-center justify-around mb-10">
				{stats.map((stat, i) => (
					<InfoBlock key={i} percentage={stat.percentage} text={stat.text} />
				))}
			</div>
		</div>
	)
}


interface InfoBlockProps {
	percentage: number
	text: string
}

export const InfoBlock = ({ percentage, text }: InfoBlockProps) => (
	<div className="min-w-max-sm w-full h-96 md:w-1/2 lg:w-1/3 flex flex-col items-center gap-4 p-4">
		<AnimatedCircle percentage={percentage} size={160} strokeWidth={6} color="#f97316" />
		<em className="text-xl md:text-2xl">{text}</em>
	</div>
)