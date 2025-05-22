import { Accordion, AccordionContent, AccordionItem, AccordionTrigger } from '../../ui/accordion'
import sideCarImage from '../../../assets/josh-berquist-_4sWbzH5fp8-unsplash.jpg'

export const FAQSection = () => {
    return (
        <section className="w-full max-w-5xl mx-auto px-4 py-18">
            <div className="flex flex-col md:flex-row gap-8">
                <div className="w-full md:w-1/2">
                    <Accordion type='single' collapsible>
                        <AccordionItem value='item-1'>
                            <AccordionTrigger>How do I pay for my rental and receive the documents?</AccordionTrigger>
                            <AccordionContent>
                                Payment is made online via credit or debit card. After payment, rental and insurance documents are automatically generated and stored in your account. You don't need to visit an office — everything is ready before pickup.
                            </AccordionContent>
                        </AccordionItem>
                        <AccordionItem value='item-2'>
                            <AccordionTrigger>Why is a deposit required for car rental?</AccordionTrigger>
                            <AccordionContent>
                                The deposit covers possible damages or contract violations. It's held on your card and refunded after the car is returned in proper condition. The amount depends on the selected vehicle and is shown during booking.
                            </AccordionContent>
                        </AccordionItem>
                        <AccordionItem value='item-3'>
                            <AccordionTrigger>What do I need to do when picking up or returning a car?</AccordionTrigger>
                            <AccordionContent>
                                At pickup and return, the vehicle's condition is inspected, and both sides sign a digital act confirming the handover or return. This ensures transparency and protects both you and the company.
                            </AccordionContent>
                        </AccordionItem>
                        <AccordionItem value='item-4'>
                            <AccordionTrigger> How do I check a car's availability on specific dates?</AccordionTrigger>
                            <AccordionContent>
                                Use the booking form on our website to instantly see which vehicles are available for your selected dates. Availability is updated in real time.
                            </AccordionContent>
                        </AccordionItem>
                    </Accordion>
                </div>
                <div className="w-full md:w-1/2 flex">
                    <div className="relative flex-1">
                        <img
                            src={sideCarImage}
                            alt="Car Picture"
                            className="absolute inset-0 w-full h-full object-cover transition-all duration-300"
                        />
                    </div>
                </div>
            </div>
        </section>
    )
}
