import { Button } from "@/components/ui/button"
import {
  Carousel,
  CarouselContent,
  CarouselItem,
} from "@/components/ui/carousel"
import Autoplay from "embla-carousel-autoplay"
import { useNavigate } from "react-router"

import slide1 from "../../assets/featuresCarousel/lvivPanorama.jpg"

const slides = [
  {
    img: slide1,
    title: "At your service",
    subtitle: "personal account",
    cta: "Learn more",
    link: "/",
  },
  {
    img: slide1,
    title: "At your service",
    subtitle: "personal account",
    cta: "Learn more",
    link: "/1",
  },
  {
    img: slide1,
    title: "At your service",
    subtitle: "personal account",
    cta: "Learn more",
    link: "/2",
  },
  {
    img: slide1,
    title: "At your service",
    subtitle: "personal account",
    cta: "Learn more",
    link: "/3",
  },
  {
    img: slide1,
    title: "At your service",
    subtitle: "personal account",
    cta: "Learn more",
    link: "/4",
  },
  {
    img: slide1,
    title: "At your service",
    subtitle: "personal account",
    cta: "Learn more",
    link: "/5",
  },
]

export function FeaturesCarousel() {
  const navigate = useNavigate()

  return (
    <div className="w-full max-w-5xl mx-auto my-8 overflow-hidden rounded-md">
      <Carousel
        className="relative w-full overflow-hidden rounded-lg"
        plugins={[Autoplay({ delay: 4000, stopOnMouseEnter: true })]}
        opts={{ loop: true, align: "start" }}
      >
        <CarouselContent className="flex">
          {slides.map((slide, i) => (
            <CarouselItem key={i} className="relative w-full flex-none h-[400px]">
              <img
                src={slide.img}
                alt={slide.title}
                className="object-cover w-full h-full"
              />
              {/* overlay */}
              <div className="absolute inset-0 bg-gradient-to-r from-black/80 via-black/30 to-transparent flex flex-col justify-center px-8">
                <h2 className="text-4xl font-bold text-white">{slide.title}</h2>
                <p className="mt-2 text-lg text-gray-200">{slide.subtitle}</p>
                <Button
                  variant="link"
                  className="mt-4 border-white text-white border-2 hover:bg-white/10 mw-32 w-64"
                  onClick={() => navigate(slide.link)}
                >
                  {slide.cta}
                </Button>
              </div>
            </CarouselItem>
          ))}
        </CarouselContent>
      </Carousel>
    </div>
  )
}