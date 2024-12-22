import { useTranslation } from 'react-i18next'
import FooterHeader from '../FooterHeader'
import FooterContent from '../FooterContent'
import FooterModule from '../FooterModule'
import TextExtraLight from '../../Controls/Text/TextExtraLight'
import TextRegular from '../../Controls/Text/TextRegular'
import FooterCopyright from '../FooterCopyright'
import styles from '../Footer.module.css'

const GuestFooter = () => {
    const { t } = useTranslation()

    return (
        <div className={styles.wrapper}>
            <div className={styles.footer}>
                <FooterHeader />
                <FooterContent
                    content={
                        <FooterModule
                            title={<TextExtraLight text={t('footer.community')} />}
                            firstOption={<TextRegular text={t('footer.sign-in')} />}
                            secondOption={<TextRegular text={t('footer.sign-up')} />}
                            thirdOption={<TextRegular text={t('footer.premium')} />}
                        />
                    }
                />
                <FooterCopyright />
            </div>
        </div>
    )
}

export default GuestFooter
