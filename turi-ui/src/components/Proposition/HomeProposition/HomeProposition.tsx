import { useTranslation } from 'react-i18next'
import PropositionContent from '../PropositionContent'
import PropositionText from '../PropositionText'
import PropositionPanels from '../PropositionPanels'
import styles from './HomeProposition.module.css'

const HomeProposition = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.proposition}>
            <PropositionContent
                title={
                    <PropositionText
                        text={t('home.proposition.stay')}
                    />
                }
                panels={
                    <PropositionPanels
                        firstText={t('home.proposition.hotels')}
                        firstImage={'src/assets/images/proposition/stay/hotel.jpeg'}
                        secondText={t('home.proposition.houses')}
                        secondImage={'src/assets/images/proposition/stay/house.jpeg'}
                        thirdText={t('home.proposition.apartments')}
                        thirdImage={'src/assets/images/proposition/stay/apartment.jpeg'}
                        fourthText={t('home.proposition.guesthouses')}
                        fourthImage={'src/assets/images/proposition/stay/guesthouse.jpeg'}
                        fifthText={t('home.proposition.privates')}
                        fifthImage={'src/assets/images/proposition/stay/private.jpeg'}
                        sixthText={t('home.proposition.b&b')}
                        sixthImage={'src/assets/images/proposition/stay/b&b.jpeg'}
                    />
                }
            />
            <PropositionContent
                title={
                    <PropositionText
                        text={t('home.proposition.attraction')}
                    />
                }
                panels={
                    <PropositionPanels
                        firstText={t('home.proposition.kayaks')}
                        firstImage={'src/assets/images/proposition/attraction/kayak.jpeg'}
                        secondText={t('home.proposition.ski-slopes')}
                        secondImage={'src/assets/images/proposition/attraction/ski-slope.jpeg'}
                        thirdText={t('home.proposition.saunas')}
                        thirdImage={'src/assets/images/proposition/attraction/sauna.jpeg'}
                        fourthText={t('home.proposition.pools')}
                        fourthImage={'src/assets/images/proposition/attraction/pool.jpeg'}
                        fifthText={t('home.proposition.quads')}
                        fifthImage={'src/assets/images/proposition/attraction/quad.jpeg'}
                        sixthText={t('home.proposition.bicycles')}
                        sixthImage={'src/assets/images/proposition/attraction/bicycle.jpeg'}
                    />
                }
            />
        </div>
    )
}

export default HomeProposition