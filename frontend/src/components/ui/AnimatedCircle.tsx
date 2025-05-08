import { useEffect, useRef, useState } from "react"

interface AnimatedCircleProps {
	percentage: number
	size?: number
	strokeWidth?: number
	color?: string
}

export const AnimatedCircle = ({
	percentage,
	size = 120,
	strokeWidth = 8,
	color = "orange"
}: AnimatedCircleProps) => {
	const [isVisible, setIsVisible] = useState(false)
	const circleRef = useRef<SVGCircleElement>(null)
	const containerRef = useRef<HTMLDivElement>(null)

	const radius = (size - strokeWidth) / 2
	const circumference = 2 * Math.PI * radius

	useEffect(() => {
		const observer = new IntersectionObserver(
			([entry]) => {
				if (entry.isIntersecting) {
					setIsVisible(true)
				}
			},
			{ threshold: 0.6 }
		)

		if (containerRef.current) {
			observer.observe(containerRef.current)
		}

		return () => observer.disconnect()
	}, [])

	useEffect(() => {
		if (isVisible && circleRef.current) {
			const offset = circumference - (percentage / 100) * circumference
			circleRef.current.style.strokeDashoffset = offset.toString()
		}
	}, [isVisible, circumference, percentage])

	return (
		<div ref={containerRef} className="flex flex-col items-center justify-center mt-10">
			<div className="relative" style={{ width: size, height: size }}>
				<svg width={size} height={size}>
					<circle
						stroke="#ccc"
						fill="transparent"
						strokeWidth={strokeWidth}
						r={radius}
						cx={size / 2}
						cy={size / 2}
					/>
					<circle
						ref={circleRef}
						stroke={color}
						fill="transparent"
						strokeWidth={strokeWidth}
						strokeLinecap="round"
						r={radius}
						cx={size / 2}
						cy={size / 2}
						strokeDasharray={circumference}
						strokeDashoffset={circumference}
						style={{ transition: "stroke-dashoffset 1.2s ease" }}
					/>
				</svg>
				<div className="absolute inset-0 flex items-center justify-center text-2xl font-bold">
					{percentage}%
				</div>
			</div>
		</div>
	)
}
