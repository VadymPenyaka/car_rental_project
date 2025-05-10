// ServiceProcess.tsx

export const TermAndAdvantagesSection = () => {
    const steps = [
        {
            title: "Online Registration",
            description:
                "Register with BankID for instant verification, or enter passport & driver’s license details for automatic system verification.",
        },
        {
            title: "Automatic Document Generation",
            description:
                "All necessary rental and insurance documents are generated automatically during booking.",
        },
        {
            title: "Vehicle Pickup",
            description:
                "Inspect the car on arrival and confirm its condition—no paperwork needed at the desk.",
        },
        {
            title: "Driving Experience Verification",
            description:
                "Our system automatically determines required driving experience based on your chosen vehicle.",
        },
        {
            title: "Payment",
            description:
                "Make secure online payments directly through our website.",
        },
        {
            title: "Contract Extension",
            description:
                "Extend your rental any time from your personal account with a single click.",
        },
        {
            title: "Full Details",
            description:
                "Always view up-to-date rental terms and pricing on our website.",
        },
        {
            title: "Vehicle Return",
            description:
                "Return inspection and acceptance act are completed together—deposit refunded if the car’s in good condition.",
        },
    ]

    return (
        <section className="w-full max-w-5xl mx-auto py-16 px-4 sm:px-6 lg:px-8">
            <h2 className="text-3xl font-bold text-center mb-12">
                How It Works
            </h2>
            <dl className="grid grid-cols-1 md:grid-cols-2 gap-8">
                {steps.map(({ title, description }, idx) => (
                    <div key={idx} className="space-y-2">
                        <dt className="text-xl font-semibold text-gray-900">
                            {idx + 1}. {title}
                        </dt>
                        <dd className="text-gray-600">{description}</dd>
                    </div>
                ))}
            </dl>
        </section>
    )
}