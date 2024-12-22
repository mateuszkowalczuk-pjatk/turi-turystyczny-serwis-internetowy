import { useTranslation } from 'react-i18next'
import FooterHeader from '../FooterHeader'
import FooterContent from '../FooterContent'
import FooterModule from '../FooterModule'
import TextExtraLight from '../../Controls/Text/TextExtraLight'
import TextRegular from '../../Controls/Text/TextRegular'
import FooterCopyright from '../FooterCopyright'
import styles from '../Footer.module.css'

const UserFooter = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.wrapper}>
            <div className={styles.footer}>
                <FooterHeader />
                <FooterContent
                    content={
                        <FooterModule
                            title={<TextExtraLight text={t('footer.your-account')} />}
                            firstOption={<TextRegular text={t('footer.reservations')} />}
                            secondOption={<TextRegular text={t('footer.stays')} />}
                            thirdOption={<TextRegular text={t('footer.favourites')} />}
                            fourthOption={<TextRegular text={t('footer.premium')} />}
                            fifthOption={<TextRegular text={t('footer.account')} />}
                        />
                    }
                />
                <FooterCopyright />
            </div>
        </div>
    )
}

export default UserFooter