import AssistanceIcon from "../../../assets/infoCards/assistance.svg"
import AffordableIcon from "../../../assets/infoCards/affordable.svg"
import BureaucracyIcon from "../../../assets/infoCards/bureaucracy.svg"
import InsuranceIcon from "../../../assets/infoCards/insurance.svg"
import ConditionIcon from "../../../assets/infoCards/condition.svg"
import ReplacementIcon from "../../../assets/infoCards/replacement.svg"

export const InfoReasonsSection = () => {
    const reasons = [
      {
        title: "Assistance",
        subtitle: "Support on the road 24/7",
        icon: AssistanceIcon,
      },
      {
        title: "Affordable prices",
        subtitle: "We try to provide lower prices than average",
        icon: AffordableIcon,
      },
      {
        title: "Minimum of bureaucracy",
        subtitle: "Few documents for lease",
        icon: BureaucracyIcon,
      },
      {
        title: "Full insurance",
        subtitle: "All cars are insured",
        icon: InsuranceIcon,
      },
      {
        title: "All cars in good condition",
        subtitle: "Our cars are regularly serviced",
        icon: ConditionIcon,
      },
      {
        title: "Car replacement",
        subtitle: "In the case of failure",
        icon: ReplacementIcon,
      },
    ];
  
    return (
      <section className="py-12 px-4 md:px-8 lg:px-16">
        <h2 className="text-2xl md:text-3xl font-bold text-center mb-10">
          Seven reasons to rent a car in <span className="text-orange-500">5Cars</span>:
        </h2>
  
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 max-w-6xl mx-auto">
          {reasons.map((reason, index) => (
            <div
              key={index}
              className="rounded-lg shadow-xl py-12 text-center flex flex-col items-center hover:shadow-lg transition-shadow"
            >
              <img src={reason.icon} alt={reason.title} className="h-16 mb-4" />
              <h3 className="text-lg font-semibold text-orange-500">{reason.title}</h3>
              <p className="text-gray-500 mt-1 text-sm">{reason.subtitle}</p>
            </div>
          ))}
        </div>
      </section>
    );
  };
  