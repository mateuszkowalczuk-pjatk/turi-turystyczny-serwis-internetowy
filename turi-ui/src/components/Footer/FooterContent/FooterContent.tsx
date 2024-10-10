import CommunityContent from "./CommunityContent";
import OfferContent from "./OfferContent";
import MoreContent from "./MoreContent";
import ContactContent from "./ContactContent";
import styles from './FooterContent.module.css'

const FooterContent = () => {
    return (
        <div className={styles.content}>
            <CommunityContent />
            <OfferContent />
            <MoreContent />
            <ContactContent />
        </div>
    )
}

export default FooterContent;