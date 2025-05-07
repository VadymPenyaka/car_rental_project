import React from "react"
import { Button } from "@/components/ui/button"
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/components/ui/carousel"
import Autoplay from "embla-carousel-autoplay"
import { useNavigate } from "react-router"

import slide1 from "../../assets/featuresCarousel/lvivPanorama.jpg"
import { link } from "fs"
// Add more slide images as needed

const slides = [
  {
    img: slide1,
    title: "До ваших послуг",
    subtitle: "особистий кабінет",
    cta: "Детальніше",
	link: "/",
  },
  {
    img: slide1,
    title: "До ваших послуг",
    subtitle: "особистий кабінет",
    cta: "Детальніше",
	link: "/1",
  },
  {
    img: slide1,
    title: "До ваших послуг",
    subtitle: "особистий кабінет",
    cta: "Детальніше",
	link: "/2",
  },
  {
    img: slide1,
    title: "До ваших послуг",
    subtitle: "особистий кабінет",
    cta: "Детальніше",
	link: "/3",
  },
  {
    img: slide1,
    title: "До ваших послуг",
    subtitle: "особистий кабінет",
    cta: "Детальніше",
	link: "/4",
  },
  {
    img: slide1,
    title: "До ваших послуг",
    subtitle: "особистий кабінет",
    cta: "Детальніше",
	link: "/5",
  },
]

export function FeaturesCarousel() {
  const navigate = useNavigate()

  return (
    <div className="w-full max-w-5xl mx-auto my-8 overflow-hidden rounded-md">
      <Carousel
        className="relative w-full"
        plugins={[Autoplay({ delay: 5000 })]}
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
              <div className="absolute inset-0 bg-black/65 flex w-2/3 flex-col justify-center px-8 bg-gradient-to-r from-black/100 to-white/5 shadow-2xl drop-shadow-2xl shadow-black/100">
                <h2 className="text-4xl font-bold text-white">{slide.title}</h2>
                <p className="mt-2 text-lg text-gray-200">{slide.subtitle}</p>
                <Button
                  variant="link"
                  className="mt-4 border-white text-white border-2 hover:bg-white/10"
                  onClick={() => navigate(slide.link)}
                >
                  {slide.cta}
                </Button>
              </div>
            </CarouselItem>
          ))}
        </CarouselContent>
        <CarouselPrevious className="absolute top-1/2 left-4 -translate-y-1/2 bg-white/30 hover:bg-white p-2 rounded-full z-10" />
        <CarouselNext className="absolute top-1/2 right-4 -translate-y-1/2 bg-white/30 hover:bg-white p-2 rounded-full z-10" />
      </Carousel>
    </div>
  )
}