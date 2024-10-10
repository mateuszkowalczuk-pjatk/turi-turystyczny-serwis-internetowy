import HeaderInformation from "./HeaderInformation";
import AccountInformation from "./AccountInformation";
import PremiumInformation from "./PremiumInformation";
import styles from './InformationText.module.css'

const InformationText = () => {
    return (
        <div className={styles.text}>
            <HeaderInformation />
            <AccountInformation />
            <PremiumInformation />
        </div>
    )
}

export default InformationText;